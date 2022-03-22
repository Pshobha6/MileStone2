package com.example.Milestone2.Controller;

import com.example.Milestone2.Model.Netflix;
import com.example.Milestone2.Util.ReadCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {
    @Autowired
    ReadCSV readCSV;

    @GetMapping("/tvshows")
    public ResponseEntity<List<Netflix>> getTVShow(
            @RequestParam(value = "count", required = false) String count,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "movieType", required = false) String movieType,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate
    ) {

        System.out.println(count+ "  "+country+ " "+movieType+" "+startDate+" ---------"+endDate);
        long responseStartTimer = System.currentTimeMillis();
        HttpHeaders responseHeader = new HttpHeaders();
        List<Netflix> showList= readCSV.listShowsBasedOnQuery(count, country, movieType, startDate, endDate);
        long responseEndTimer = System.currentTimeMillis() - responseStartTimer;
        responseHeader.set("X-TIME-TO-EXECUTE", String.format("%s ms", responseEndTimer));
        return ResponseEntity.ok().headers(responseHeader).body(showList);
    }

//
//    @GetMapping("/tvshowss")
//   // @RequestMapping(value = "tvshows", method = RequestMethod.GET)
//    public  List<Netflix> getTvShowsByCount(@RequestParam("count") Integer count){
//        List<Netflix> mLists= readCSV.readCSVContent();
//
//
//        return mLists.stream().limit(count).collect(Collectors.toList());
//    }
//
//    @GetMapping("/tvshows/movie")
//    public  List<Netflix> getTvShowsByMovie(@RequestParam("movieType") String movieType){
//        List<Netflix> mLists= readCSV.readCSVContent();
//
//        return mLists.stream().filter(movie -> movie.getListed_in().contains(movieType)).collect(Collectors.toList());
//
//
//    }
//    @GetMapping("/tvshows/country")
//    public  List<Netflix> getTvShowsByCountry(@RequestParam("country") String country){
//        List<Netflix> mLists= readCSV.readCSVContent();
//
//        return mLists.stream().filter(movie -> movie.getCountry().contains(country)).collect(Collectors.toList());
//
//
//    }
//    @GetMapping("/tvshows/date")
//    public  List<Netflix> getTvShowsByCountry(@RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") Date startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") Date endDate) throws ParseException {
//        List<Netflix> mLists= readCSV.readCSVContent();
//        SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yy");
////        Date sDate=sdf.parse(startDate);
////        Date eDate=sdf.parse(endDate);
//
//
//        return mLists.stream().filter(movie -> {
//            boolean con=true;
//            try {
//                con= sdf.parse(movie.getDate_added()).compareTo(startDate)>=0 && sdf.parse(movie.getDate_added()).compareTo(endDate)<=0;
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return con;
//        }).collect(Collectors.toList());
//
//
//    }

}
