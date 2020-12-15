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

package cl.ucn.disc.dsm.rramos.news.model;

import net.openhft.hashing.LongHashFunction;

import org.threeten.bp.ZonedDateTime;

public class News {

    private Long id;

    private String title;

    private String sources;

    private String author;

    private String url;

    private String urlImage;

    private String description;

    private String content;

    private ZonedDateTime publishedAt;

    public News(String title, String sources, String author, String url, String urlImage,
                String description, String content, ZonedDateTime publishedAt) {

        Validation.minSize(title, 2, "titlle");
        this.title = title;

        Validation.minSize(sources, 2, "source");
        this.sources = sources;

        Validation.minSize(author, 2, "author");
        this.author = author;

        this.id = LongHashFunction.xx()
                .hashChars(title + "|" + sources + "|" + author);

        this.url = url;
        this.urlImage = urlImage;

        Validation.minSize(description, 10, "description");
        this.description = description;

        Validation.notNull(content, "content");
        this.content = content;

        Validation.notNull(publishedAt, "publishedAt");
        this.publishedAt = publishedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSources() {
        return sources;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public ZonedDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishedAt(ZonedDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
}
