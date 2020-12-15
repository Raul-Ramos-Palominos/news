/*
 * Copyright (c) 2020. Raul Ramos-Palominos raul.ramos@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 */

package cl.ucn.disc.dsm.rramos.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.AsyncTask;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ModelAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import cl.ucn.disc.dsm.rramos.news.model.News;
import cl.ucn.disc.dsm.rramos.news.model.NewsItem;
import cl.ucn.disc.dsm.rramos.news.services.Contracts;
import cl.ucn.disc.dsm.rramos.news.services.ContractsImplNewsApi;

public class MainActivity extends AppCompatActivity {

    private static final Logger log = LoggerFactory.getLogger(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setSupportActionBar(findViewById(R.id.am_t_toolbar));

        ModelAdapter<News, NewsItem> newsAdapter = new ModelAdapter<>(NewsItem::new);
        FastAdapter<NewsItem> fastAdapter = FastAdapter.with(newsAdapter);
        fastAdapter.withSelectable(false);

        RecyclerView recyclerView = findViewById(R.id.am_rv_news);
        recyclerView.setAdapter(fastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        AsyncTask.execute(() -> {

            Contracts contracts = new ContractsImplNewsApi("89757d22e41d4e8c99aa21dfad3dbe89");

            List<News> listNews = contracts.retrieveNews(30);

            runOnUiThread(() -> {
                newsAdapter.add(listNews);
            });
        });
    }
}