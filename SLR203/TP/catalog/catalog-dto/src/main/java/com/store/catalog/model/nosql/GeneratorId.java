package com.store.catalog.model.nosql;

/**
 * Created by zouheir on 11/11/15.
 */
public class GeneratorId {




    public static void main(String[] args){

        String[] array = new String[]{
                "Motorola XOOM with Wi-Fi"
                ,"MOTOROLA XOOM",
                "MOTOROLA ATRIX 4G"
                ,"Dell Streak 7"
                ,"Samsung Gem"
                ,"Dell Venue"
                ,"Nexus S"
                ,"LG Axis"
                ,"Samsung Galaxy Tab","Samsung Showcase a Galaxy S phone"
                ,"DROID 2 Global by Motorola"
                ,"DROID Pro by Motorola"
                ,"MOTOROLA BRAVO with MOTOBLUR"
                ,"Motorola DEFY with MOTOBLUR"
                ,"T-Mobile myTouch 4G"
                ,"Samsung Mesmerize a Galaxy S phone"
                ,"SANYO ZIO"
                ,"Samsung Transform"
                ,"T-Mobile G2"
                ,"Motorola CHARM with MOTOBLUR"}  ;



        for (String string : array) {
            SearchableItem item = new SearchableItem();
            item.setName(string);

            System.out.println("{\"create\" : {\"_index\":\"catalog\", \"_type\":\"phone\", \"_id\":\""  + item.createId() + "\"}}");
        }

    }
}
