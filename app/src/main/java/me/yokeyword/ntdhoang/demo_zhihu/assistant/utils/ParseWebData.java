package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import java.io.IOException;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Hero;
import me.yokeyword.ntdhoang.demo_zhihu.ui.viewmodel.LeagueViewModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by FRAMGIA\tran.hoa.binh on 10/04/2018.
 */

public class ParseWebData {
    public static void loader(LeagueViewModel leagueViewModel) throws IOException {
        Document doc = Jsoup.connect("https://lienquan.garena.vn/tuong").get();
        Element formElement = doc.getElementById("contactForm");
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
}
