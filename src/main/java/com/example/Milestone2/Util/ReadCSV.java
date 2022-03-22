package com.example.Milestone2.Util;

import com.example.Milestone2.Model.Netflix;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReadCSV {

    public  List<Netflix> readCSVContent(){
        List<Netflix> movieLists=new ArrayList<>();
        String file="src/main/resources/netflix_titles.csv";
        try {
            System.out.println("Reading csv content-------");
            BufferedReader br=new BufferedReader(new FileReader(file));

            System.out.println(Arrays.stream(br.readLine().split(","))+"  "+br.readLine().split(",").length);

            while(br.readLine()!=null && !br.readLine().equalsIgnoreCase("")){
                String line=br.readLine();
                if(line!=null) {
                    String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
//                    System.out.println(values.length);
                    if (values.length == 12) {
                        Netflix movie = new Netflix(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11]);
                        movieLists.add(movie);
                    }
                }
            }


            System.out.println(movieLists.toArray().length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return movieLists;
    }
    public  List<Netflix> listShowsBasedOnQuery(
            String count, String country, String movieType, String startDate, String endDate
    ) {
        long countInput=(count != null)? Integer.parseInt(count): 0;
        List<Netflix> resultantList = new ArrayList<>();





        List<Netflix> mLists = readCSVContent();
        if(count!=null) {
            resultantList=mLists.stream().limit(countInput).collect(Collectors.toList());

        }

        if(movieType != null) {


            resultantList=mLists.stream().filter(movie -> movie.getListed_in().contains(movieType)).collect(Collectors.toList());

        }

        if(country != null) {
            resultantList =mLists.stream().filter(movie -> movie.getCountry().contains(country)).collect(Collectors.toList());

        }

        if(startDate != null && endDate != null) {
//            DateFormat date = new SimpleDateFormat("dd/M/Y");
//            DateFormat csvDate = new SimpleDateFormat("\"MMM d, y\"");
            DateFormat date = new SimpleDateFormat("dd-MMM-yy");
            DateFormat csvDate = new SimpleDateFormat("dd-MMM-yy");
            try{
                Date initialParseDate = date.parse(startDate);
                Date finalParseDate = date.parse(endDate);
                SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yy");
//        Date sDate=sdf.parse(startDate);
//        Date eDate=sdf.parse(endDate);


                return mLists.stream().filter(movie -> {
                    boolean con=true;
                    try {
                        con= movie.getDate_added().compareTo(sdf.parse(startDate))>=0 && movie.getDate_added().compareTo(sdf.parse(endDate))<=0;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return con;
                }).collect(Collectors.toList());

            } catch (ParseException e) {
                e.getMessage();
            }


        }

        return resultantList;
    }
}

