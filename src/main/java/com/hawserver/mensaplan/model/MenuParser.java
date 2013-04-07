package com.hawserver.mensaplan.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * MenuParser gets the html from the page and parses it to a list of menu items.
 * 
 * @author Oliver
 *
 */
public class MenuParser {

    /*
     * @TODO: move to properties file for configuration
     */
    private final String menuBaseUrl = "http://speiseplan.studwerk.uptrade.de/de/530/2013/";
    private static final Logger LOG = Logger.getLogger(MenuParser.class);
    private int thisWeek = new DateTime().getWeekOfWeekyear();
    private int nextWeek = thisWeek + 1;

    public MenuParser() {
    }

    public List<MenuItem> getCurrentMenuItems() {
        List<MenuItem> result=new ArrayList<MenuItem>();
        result.addAll(getMenuItems(menuBaseUrl,thisWeek));
        result.addAll(getMenuItems(menuBaseUrl,nextWeek));
        return result;
    }
    
    protected List<DateMidnight> getDaysForWeek(int week){
        List<DateMidnight> result = new ArrayList<DateMidnight>();
        DateMidnight firstDayOfWeek=new DateMidnight().withWeekOfWeekyear(week).withDayOfWeek(1);
        for (int i = 0; i <= 5; i++) {
            result.add(firstDayOfWeek.plusDays(i));
        }
        return result;
    }

    private List<MenuItem> getMenuItems(String url,int week) {
        List<MenuItem> result = new ArrayList<MenuItem>();
        try {
            Document doc = Jsoup.connect(url+week+"/").get();
            Elements categories = doc.select("tbody tr");
            for (Element categoryElement : categories) {
                String categoryString = categoryElement.select(".category")
                        .text();
                Iterator<DateMidnight> iterator = getDaysForWeek(week).iterator();
                for (Element day : categoryElement.select(".day")) {
                    MenuItem menuItem = getMenuItemForDay(day, iterator.next(),
                            categoryString);
                    if (menuItem != null)
                        result.add(menuItem);
                }
            }
        } catch (IOException e) {
            LOG.error("Error while parsing\n" + e.getMessage() + "\n"
                    + e.getCause());
        }
        return result;
    }

    private MenuItem getMenuItemForDay(Element html, DateMidnight date,
            String category) {
        MenuItem result = null;
        Element dish = html.select(".dish").first();
        if (dish != null) {
            String description = dish.select("strong").text();
            String attributes=getAttributesFromElements(dish.select("img"));
            List<Integer> prices = getPricesFromString(dish.select(".price")
                    .first().text());
            if (prices.size() == 2) {
                result = new MenuItem(date.toDate(), prices.get(0),
                        prices.get(1), description, category, attributes);
            }
        }
        return result;
    }

    private String getAttributesFromElements(Elements elements) {
        List<String> attributes=new ArrayList<String>();
        for (Element img : elements) {
            attributes.add(img.attr("title"));
        }
        return StringUtils.join(attributes, "|");
    }

    protected List<Integer> getPricesFromString(String text) {
        List<Integer> result = new ArrayList<Integer>();
        String[] array = text.split("/");
        if (array.length == 2) {
            String regex = "[/€,\\s\\xA0]";
            String studentPriceString = array[0].replaceAll(regex, "");
            String nonStudentPriceString = array[1].replaceAll(regex, "");
            try {
                int studentPrice = Integer.parseInt(studentPriceString);
                int nonStudentPrice = Integer.parseInt(nonStudentPriceString);
                result.add(studentPrice);
                result.add(nonStudentPrice);
            } catch (NumberFormatException e) {
                LOG.debug("Could not parse prices from: \"" + text + "\"");
            }
        }
        return result;
    }

}
