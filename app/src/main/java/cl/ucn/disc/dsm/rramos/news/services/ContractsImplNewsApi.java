/*
 * Copyright (c) 2020. Raul Ramos-Palominos raul.ramos@alumnos.ucn.cl
 *                            
 *                            Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *                            
 *                            The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *                            
 *                            THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *                            
 *
 */

package cl.ucn.disc.dsm.rramos.news.services;

import com.kwabenaberko.newsapilib.models.Article;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cl.ucn.disc.dsm.rramos.news.model.News;
import cl.ucn.disc.dsm.rramos.news.model.Validation;

public class ContractsImplNewsApi implements Contracts {
    private static final Logger log = LoggerFactory.getLogger(ContractsImplNewsApi.class);

    private final NewsApiService newsApiService;

    public ContractsImplNewsApi(final String theApiKey) {

        Validation.minSize(theApiKey, 10, "ApiKey !!");

        this.newsApiService = new NewsApiService(theApiKey);
    }

    private static News toNews(final Article article) {

        Validation.notNull(article, "Article null !?!");

        boolean needFix = false;

        if(article.getAuthor() == null) {
            article.setAuthor("No author*");
            needFix = true;
        }

        if(article.getDescription() == null || article.getDescription().length() == 0) {
            article.setDescription("No description*");
            needFix = true;
        }

        if(needFix) {
            log.warn("Article with invalid restrictions: {}.", ToStringBuilder.reflectionToString(
                    article, ToStringStyle.MULTI_LINE_STYLE            ));
        }

        ZonedDateTime publishedAt = ZonedDateTime.parse(article.getPublishedAt())
                .withZoneSameInstant(ZoneId.of("-3"   ));

        if(article.getAuthor() == null || article.getAuthor().length() == 0){
            log.warn("Article without author !!");
            return null;
        }
        if(article.getDescription() == null){
            log.warn("Description without author !!");
            return null;
        }

        return new News(
                article.getTitle(),
                article.getSource().getName(),
                article.getAuthor(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getDescription(),
                article.getDescription(), // FIXME: Where is the content?
                publishedAt
        );
    }

    private static <T> Predicate<T> distinctById(Function<? super T, ?> idExtractor){
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(idExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public List<News> retrieveNews(Integer size) {
        try{
            List<Article> articles = newsApiService.getTopHeadlines(
                    "general", size
            );

            List<News> news = new ArrayList<>();

            for(Article article : articles) {
                news.add(toNews(article));
            }
            return news.stream().filter(distinctById(News::getId))
                    .sorted((k1, k2) -> k2.getPublishedAt().compareTo(k1.getPublishedAt()))
                    .collect(Collectors.toList());
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void saveNews(News news) {
        throw new NotImplementedException("Can't save in NewsAPI");
    }
}
