package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.StringDef;
import android.util.Log;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.BaseEntity;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Item;
import me.yokeyword.ntdhoang.demo_zhihu.ui.viewmodel.LeagueViewModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.reactivestreams.Subscription;

/**
 * Created by FRAMGIA\tran.hoa.binh on 10/04/2018.
 */

public class ParseWebData {
    public static final String CAP1 = "cap-1";
    public static final String CAP2 = "cap-2";
    public static final String CAP3 = "cap-3";

    @StringDef({ CAP1, CAP2, CAP3 })
    public @interface Level {
    }

    public static final String ITEM_CONG = "cong";
    public static final String ITEM_PHEP = "phep";
    public static final String ITEM_THU = "thu";
    public static final String ITEM_TOCDO = "toc-do";
    public static final String ITEM_DIRUNG = "di-rung";

    @StringDef({ ITEM_CONG, ITEM_PHEP, ITEM_THU, ITEM_TOCDO, ITEM_DIRUNG })
    public @interface ItemType {
    }

    public static final String urlTuong = "https://lienquan.garena.vn/tuong";
    public static final String urlTrangbi = "https://lienquan.garena.vn/trang-bi";

    public static void loaderHero(LeagueViewModel leagueViewModel) throws IOException {
        Document doc = Jsoup.connect(urlTuong).get();
        Elements inputElements = doc.select("div.bxlisthero>ul").select("li").select("p");
        Elements sourceElements = doc.select("div.bxlisthero>ul").select("li").select("a[href]");

        int size = inputElements.size();
        for (int i = 0; i < size; i++) {
            Hero hero = new Hero();
            String id = inputElements.get(i).attr("data-id");
            String name = inputElements.get(i).attr("data-name");
            String type = inputElements.get(i).attr("data-type");
            String link = sourceElements.get(i).attr("href");
            String image = sourceElements.get(i).select("img[src]").attr("src");
            hero.setName(name);
            hero.setType(type);
            hero.setLink(link);
            hero.setImgRes(image);
            leagueViewModel.insertLeague(hero);
        }
    }

    public static void loaderItem(LeagueViewModel leagueViewModel) throws IOException {
        Document doc = Jsoup.connect(urlTrangbi).get();
        Elements parentElements = doc.select("div.bxlisthero>ul.list-item").select("li");
        Elements contentElements = doc.select("div.bxlisthero>ul.list-item");

        int size = parentElements.size();
        for (int i = 0; i < size; i++) {
            Item item = new Item();
            String threeAttr = parentElements.get(i).select("span").text();
            String[] splitted = threeAttr.split(",");
            String level = splitted[0];
            String type = splitted[1];
            String name = splitted[3];
            String image = parentElements.get(i).select("img[src]").attr("src");

            String content = contentElements.get(i).getElementsByClass("txt").get(i).text();
            item.setName(name);
            item.setType(type);
            item.setContent(content);
            item.setLevel(level);
            item.setImgRes(image);
            leagueViewModel.insertItem(item);
        }
    }

    public static void loaderHeroImageSrc(LeagueViewModel leagueViewModel, Context context)
            throws IOException {
        leagueViewModel.getLeagues()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<Hero>>() {
                    List<Hero> data;

                    @Override
                    public void onNext(List<Hero> heroes) {
                        data = heroes;
                        test(data);
                    }

                    @Override
                    public void onError(Throwable t) {
                        // TODO: 11/04/2018
                        Log.d("BINH", "onError(22) called with: t = [" + t + "]");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("BINH", "onComplete(22) called");
                    }
                });
    }

    public static void loaderItemImageSrc(LeagueViewModel leagueViewModel, Context context)
            throws IOException {
        leagueViewModel.getItems()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<List<Item>>() {
                    @Override
                    public void onNext(List<Item> items) {
                        for (Item i : items) {
                            getImage(i, "item");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void test(List<Hero> data) {
        List<Flowable<String>> datas = createListHero(data);
        Flowable.mergeDelayError(datas, 1).subscribe(new FlowableSubscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String hero) {
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private static List<Flowable<String>> createListHero(List<Hero> data) {
        List<Flowable<String>> d = new ArrayList<>();
        for (Hero h : data) {
            d.add(createData(h));
        }
        return d;
    }

    private static Flowable<String> createData(Hero hero) {
        return Flowable.just(getImage(hero, "hero"));
    }

    public static String getImage(BaseEntity baseEntity, String folderName) {
        int count;
        try {
            URL url = new URL("https://lienquan.garena.vn" + baseEntity.getImgRes());
            URLConnection conexion = url.openConnection();
            conexion.connect();
            String targetFileName =
                    baseEntity.getName() + "_full" + ".jpg";//Change name and subname
            int lenghtOfFile = conexion.getContentLength();
            String downloadFolder = folderName;
            String PATH = Environment.getExternalStorageDirectory()
                    + "/"
                    + "league"
                    + "/"
                    + downloadFolder
                    + "/";
            File folder = new File(PATH);
            if (!folder.exists()) {
                folder.mkdir();//If there is no folder it will be created.
            }
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(PATH + targetFileName);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            Log.d("BINH", "doInBackground() called with: aurl = [" + PATH + "]");
        } catch (Exception e) {
            Log.d("BINH1", "doInBackground() called with: aurl = [" + e + "]");
        }
        return baseEntity.getImgRes();
    }
}
