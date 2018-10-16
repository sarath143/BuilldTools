package buildtools.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class Practice {

    public static void main(String args[]){

        List<String> fruits1 = Arrays.asList("Apple","Apricot","Avocado","Banana","Bilberry","Blackberry","Blackcurrant","Blueberry","Boysenberry","Currant","Cherry","Cherimoya","Chico fruit","Cloudberry","Coconut","Cranberry","Cucumber","Sugar apple","Damson","Date","Dragonfruit","Durian","Elderberry","Feijoa","Fig","Goji berry","Gooseberry","GrapeRaisin","Grapefruit","Guava","Honeyberry","Huckleberry","Jabuticaba","Jackfruit","Jambul","Jujube","Juniper berry","Kiwifruit","Kumquat","Lemon","Lime","Loquat","Longan","Lychee","Mango","Marionberry","Water melon","Miracle fruit","Mulberry","Nectarine","Nance","Olive","OrangeBlood","Papaya","Passionfruit","Peach","Pear","Persimmon","Physalis","Plantain","PlumPrune","Pineapple","Plumcot (or Pluot)","Pomegranate","Pomelo","Purple mangosteen","Quince","RaspberrySalmonberry","Rambutan","Redcurrant","Salal berry","Salak","Satsuma","Soursop","Star fruit","Solanum quitoense","Strawberry","Tamarillo","Tamarind","Ugli fruit","Yuzu","Avocado","Chili pepper","Corn kernel","Cucumber","Eggplant","Olive","Pea","Squash","Sunflower seed","Tomato");
        List<String> fruits2 = Arrays.asList("Apple","Apricot","Banana","Bilberry","Blackberry","Blackcurrant","Blueberry","Boysenberry","Currant","Cherry","Cherimoya","Chico fruit","Cloudberry","Coconut","Cranberry","Cucumber","Sugar apple","Damson","Date","Dragonfruit","Durian","Elderberry","Feijoa","Fig","Goji berry","Gooseberry","GrapeRaisin","Grapefruit","Guava","Honeyberry","Huckleberry","Jabuticaba","Jackfruit","Jambul","Jujube","Juniper berry","Kiwifruit","Kumquat","Lemon","Lime","Loquat","Longan","Lychee","Mango","Marionberry","Water melon","Miracle fruit","Mulberry","Nectarine","Nance","Olive","OrangeBlood","Papaya","Passionfruit","Peach","Pear","Persimmon","Physalis","Plantain","PlumPrune","Pineapple","Plumcot (or Pluot)","Pomegranate","Pomelo","Purple mangosteen","Quince","RaspberrySalmonberry","Rambutan","Redcurrant","Salal berry","Salak","Satsuma","Soursop","Star fruit","Solanum quitoense","Strawberry","Tamarillo","Tamarind","Ugli fruit","Yuzu","Avocado","Chili pepper","Corn kernel","Cucumber","Eggplant","Olive","Pea","Squash","Sunflower seed","Tomato","Avocado");

        IntStream.range(0, fruits1.size()).parallel()
                .mapToObj(Integer::new)
                .map(query ->
                        CompletableFuture.supplyAsync(()->fruits1.get(query))   //Execution query for database1
                        .thenCombineAsync(
                                CompletableFuture.supplyAsync(()->fruits2.get(query)), //Execution query for database2
                                (result1, result2) -> {
                                    if(result1.equals(result2))
                                        return Optional.of(result1);
                                    else
                                        return Optional.empty();
                                })

                )
                .map(CompletableFuture::join)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(result -> System.out.println(result));

        System.out.println("Program completed");
    }

}