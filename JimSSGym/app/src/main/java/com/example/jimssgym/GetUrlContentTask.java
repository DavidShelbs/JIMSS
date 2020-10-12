//package com.example.jimssgym;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.os.AsyncTask;
//import android.util.Pair;
//import android.widget.TextView;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class GetUrlContentTask extends AsyncTask<Object, Integer, Pair<QuickScanCardViewModel, Drawable>> {
//
//    Drawable result;
//    QuickScanCardViewModel m;
//
//     protected Pair<QuickScanCardViewModel, Drawable> doInBackground(Object... urls) {
//        String url = (String) urls[0];
//        QuickScanCardViewModel m = (QuickScanCardViewModel) urls[1];
//        InputStream is = null;
//        try {
//            is = (InputStream) new URL(url).getContent();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        result = Drawable.createFromStream(is, "src name");
//        m.setImg(result);
//        Pair<QuickScanCardViewModel, Drawable> pair = new Pair<>(m, result);
//        return pair;
//    }
//
//    protected void onProgressUpdate(Integer... progress) {
//    }
//
//    protected void onPostExecute(Pair<QuickScanCardViewModel, Drawable> result) {
//        // this is executed on the main thread after the process is over
//        // update your UI here
//        result.first.setImg(result.second);
//    }
//}