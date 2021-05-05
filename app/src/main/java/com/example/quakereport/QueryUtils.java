package com.example.quakereport;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils
{
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private QueryUtils()
    {

    }

    public  static List<WordsList> fetchEarthQuakeData(String requestUrl)
    {
        URL url=createUrl(requestUrl);
        String JsonResponse =null;
        try
        {
            JsonResponse=makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG,"problem making http request",e);
        }
        List<WordsList> earthquakes=extractFeaturesFromJson(JsonResponse);
        return earthquakes;
    }

    private static URL createUrl(String stringUrl)
    {
        URL url=null;
        try
        {
            url=new URL(stringUrl);
        }
        catch (MalformedURLException e)
        {
            Log.e(LOG_TAG,"Problem building the url:",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse="";
        if (url == null)
        {
            return jsonResponse;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try
        {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG,"Error Code:"+urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG,"Problem retriving earthQuake Json results:",e);
        }
        finally {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;

    }
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line=reader.readLine();
            while (line!=null)
            {
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }
    private static List<WordsList> extractFeaturesFromJson(String earthquakeJson)
    {
        if(TextUtils.isEmpty(earthquakeJson))
        {
            return null;
        }
        List<WordsList> earthQuakeList =new ArrayList<>();
        try
        {
            JSONObject baseJSONObject=new JSONObject(earthquakeJson);
            JSONArray earthQuakeArray=baseJSONObject.getJSONArray("features");
            for(int i=0;i<=earthQuakeArray.length();i++)
            {
                JSONObject currentEarthQuake=earthQuakeArray.getJSONObject(i);
                JSONObject properties= currentEarthQuake.getJSONObject("properties");
                double magnitude=properties.getDouble("mag");
                String location= properties.getString("place");
                long time=properties.getLong("time");
                String url=properties.getString("url");
                WordsList earthquake=new WordsList(magnitude,location,time,url);
                earthQuakeList.add(earthquake);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG,"problem parsing the json response :",e);
        }
        return earthQuakeList;
    }



}
