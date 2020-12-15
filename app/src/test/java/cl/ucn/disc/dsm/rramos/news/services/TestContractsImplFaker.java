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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.List;

import cl.ucn.disc.dsm.rramos.news.model.News;

public class TestContractsImplFaker {

    private static final Logger log = LoggerFactory.getLogger(TestContractsImplFaker.class);

    @Test
    public void testRetrieveNews(){
        log.debug("Testing ..");

        Contracts contracts = new ContractsImplFaker();

        List<News> news = contracts.retrieveNews(5);

        Assertions.assertNotNull(news, "List was null");

        Assertions.assertTrue(news.size() != 0, "Empty list?");

        Assertions.assertEquals(5, news.size(), "List size != 5");

        for(News n: news){
            log.debug("News: {}", ToStringBuilder.reflectionToString(n, ToStringStyle.MULTI_LINE_STYLE));
        }

        Assertions.assertEquals(0,contracts.retrieveNews(0).size(),"List != 0");

        Assertions.assertEquals(3,contracts.retrieveNews(3).size(),"List != 3");

        Assertions.assertTrue(contracts.retrieveNews(10).size() <= 10,"List != 10");

        log.debug("Done.");
    }

    @Test
    public void testSaveNews() {
        log.debug("Testing ..");

        Contracts contracts = new ContractsImplFaker();

        Assertions.assertThrows(IllegalArgumentException.class,() -> contracts.saveNews(null));

        int size = contracts.retrieveNews(1000).size();
        log.debug("Size: {}.", size);

        News news = new News(
                "The Title",
                "The Source",
                "The Author",
                null,
                null,
                "The Description",
                "The Content",
                ZonedDateTime.now(ZoneId.of("-3"   )));

        contracts.saveNews(news);

        int newSize = contracts.retrieveNews(1000).size();

        Assertions.assertEquals(size + 1, newSize, "Wrong size!");

        Assertions.assertThrows(IllegalArgumentException.class, () -> contracts.saveNews(news));

        log.debug(".. Done.");
    }
}
