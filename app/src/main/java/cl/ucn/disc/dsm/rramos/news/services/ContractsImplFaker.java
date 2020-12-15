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

import com.github.javafaker.Faker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cl.ucn.disc.dsm.rramos.news.model.News;
import cl.ucn.disc.dsm.rramos.news.model.Validation;

public class ContractsImplFaker implements Contracts {

    private static final Logger log = LoggerFactory.getLogger(ContractsImplFaker.class);

    private final List<News> theNews = new ArrayList<>();

    public ContractsImplFaker() {
        final Faker faker = Faker.instance();

        for (int i = 0; i < 5; i++) {
            this.theNews.add(new News(
                    faker.book().title(),
                    faker.name().username(),
                    faker.name().fullName(),
                    faker.internet().url(),
                    faker.internet().avatar(),
                    faker.harryPotter().quote(),
                    faker.lorem().paragraph(3),
                    ZonedDateTime.now(ZoneId.of("-3"))
            ));
        }
    }

    @Override
    public List<News> retrieveNews(Integer size) {
        if (size > theNews.size()) {
            return Collections.unmodifiableList(this.theNews);
        }
        return Collections.unmodifiableList(
                theNews.subList(theNews.size() - size, theNews.size())
        );
    }

    @Override
    public void saveNews(News news) {
        Validation.notNull(news, "news");
        for (News n : this.theNews) {
            if (n.getId().equals(news.getId())) {
                throw new IllegalArgumentException("Can't allow duplicate news!");
            }
        }
        this.theNews.add(news);
    }
}
