package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View view) {
        new DownloadUpdate().execute();
    }


    private class DownloadUpdate extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "http://t.weather.sojson.com/api/weather/city/101040100";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;

            try {
                URL url = new URL(stringUrl);

                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(String temperature) {

            //Update the temperature displayed
            int High = temperature.indexOf("高温");
            int Low  = temperature.indexOf("低温");

            int H0S = temperature.indexOf("high" , High);
            int L0S = temperature.indexOf("low" , Low);
            int H0E = temperature.indexOf("℃" , H0S);
            int L0E = temperature.indexOf("℃" , L0S);
            String TH0 = temperature.substring(H0S + 10 , H0E );
            String TL0 = temperature.substring(L0S + 9 , L0E );
            ((TextView) findViewById(R.id.temperature_of_the_day)).setText(TL0 + "~" + TH0);

            int H1S = temperature.indexOf("high" , H0E);
            int L1S = temperature.indexOf("low" , L0E);
            int H1E = temperature.indexOf("℃" , H1S);
            int L1E = temperature.indexOf("℃" , L1S);
            String TH1 = temperature.substring(H1S + 10 , H1E );
            String TL1 = temperature.substring(L1S + 9 , L1E );
            ((TextView) findViewById(R.id.tv_temperature_after1)).setText(TL1 + "~" + TH1);

            int H2S = temperature.indexOf("high" , H1E);
            int L2S = temperature.indexOf("low" , L1E);
            int H2E = temperature.indexOf("℃" , H2S);
            int L2E = temperature.indexOf("℃" , L2S);
            String TH2 = temperature.substring(H2S + 10 , H2E );
            String TL2 = temperature.substring(L2S + 9 , L2E );
            ((TextView) findViewById(R.id.tv_temperature_after2)).setText(TL2 + "~" + TH2);

            int H3S = temperature.indexOf("high" , H2E);
            int L3S = temperature.indexOf("low" , L2E);
            int H3E = temperature.indexOf("℃" , H3S);
            int L3E = temperature.indexOf("℃" , L3S);
            String TH3 = temperature.substring(H3S + 10 , H3E );
            String TL3 = temperature.substring(L3S + 9 , L3E );
            ((TextView) findViewById(R.id.tv_temperature_after3)).setText(TL3 + "~" + TH3);

            int H4S = temperature.indexOf("high" , H3E);
            int L4S = temperature.indexOf("low" , L3E);
            int H4E = temperature.indexOf("℃" , H4S);
            int L4E = temperature.indexOf("℃" , L4S);
            String TH4 = temperature.substring(H4S + 10 , H4E );
            String TL4 = temperature.substring(L4S + 9 , L4E );
            ((TextView) findViewById(R.id.tv_temperature_after4)).setText(TL4 + "~" + TH4);

            //Update Date
            int TimeStart = temperature.indexOf("time");
            int TimeEnd   = temperature.indexOf("cityInfo" ,TimeStart);
            String Time   = temperature.substring(TimeStart + 7 ,TimeEnd - 12);
            ((TextView) findViewById(R.id.tv_date)).setText(Time);

            //Update Forecast
            int F0S = temperature.indexOf("type");
            int F0E = temperature.indexOf("notice" , F0S);
            int F1S = temperature.indexOf("type" , F0E);
            int F1E = temperature.indexOf("notice" , F1S);
            String Forecast1 = temperature.substring(F1S + 7 , F1E - 3);
            switch(Forecast1)
            {
                case "晴"  :((ImageView) findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
                            ((LinearLayout)findViewById(R.id.background)).setBackground(getResources().getDrawable(R.drawable.sunny));
                            ((TextView) findViewById(R.id.weather)).setText("Sunny");break;
                case "小雨":((ImageView) findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
                            ((LinearLayout)findViewById(R.id.background)).setBackground(getResources().getDrawable(R.drawable.rainy));
                            ((TextView) findViewById(R.id.weather)).setText("Rainy");break;
                case "多云":((ImageView) findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
                            ((LinearLayout)findViewById(R.id.background)).setBackground(getResources().getDrawable(R.drawable.cloudy));
                            ((TextView) findViewById(R.id.weather)).setText("Cloudy");break;
                case "阴"  :((ImageView) findViewById(R.id.img_weather_condition)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
                    ((LinearLayout)findViewById(R.id.background)).setBackground(getResources().getDrawable(R.drawable.windy));
                            ((TextView) findViewById(R.id.weather)).setText("Windy");break;
            }

            int F2S = temperature.indexOf("type" , F1E);
            int F2E = temperature.indexOf("notice" , F2S);
            String Forecast2 = temperature.substring(F2S + 7 , F2E - 3);
            switch(Forecast2)
            {
                case "晴"  :((ImageView) findViewById(R.id.img_weather_after1)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
                    break;
                case "小雨":((ImageView) findViewById(R.id.img_weather_after1)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
                    break;
                case "多云":((ImageView) findViewById(R.id.img_weather_after1)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
                    break;
                case "阴"  :((ImageView) findViewById(R.id.img_weather_after1)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
                    break;
            }

            int F3S = temperature.indexOf("type" , F2E);
            int F3E = temperature.indexOf("notice" , F3S);
            String Forecast3 = temperature.substring(F3S + 7 , F3E - 3);
            switch(Forecast3)
            {
                case "晴"  :((ImageView) findViewById(R.id.img_weather_after2)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
                    break;
                case "小雨":((ImageView) findViewById(R.id.img_weather_after2)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
                    break;
                case "多云":((ImageView) findViewById(R.id.img_weather_after2)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
                    break;
                case "阴"  :((ImageView) findViewById(R.id.img_weather_after2)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
                    break;
            }


            int F4S = temperature.indexOf("type" , F3E);
            int F4E = temperature.indexOf("notice" , F4S);
            String Forecast4 = temperature.substring(F4S + 7 , F4E - 3);
            switch(Forecast4)
            {
                case "晴"  :((ImageView) findViewById(R.id.img_weather_after3)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
                    break;
                case "小雨":((ImageView) findViewById(R.id.img_weather_after3)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
                    break;
                case "多云":((ImageView) findViewById(R.id.img_weather_after3)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
                    break;
                case "阴"  :((ImageView) findViewById(R.id.img_weather_after3)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
                    break;
            }

            int F5S = temperature.indexOf("type" , F4E);
            int F5E = temperature.indexOf("notice" , F5S);
            String Forecast5 = temperature.substring(F5S + 7 , F5E - 3);
            switch(Forecast5)
            {
                case "晴"  :((ImageView) findViewById(R.id.img_weather_after4)).setImageDrawable(getResources().getDrawable(R.drawable.sunny_small));
                    break;
                case "小雨":((ImageView) findViewById(R.id.img_weather_after4)).setImageDrawable(getResources().getDrawable(R.drawable.rainy_small));
                    break;
                case "多云":((ImageView) findViewById(R.id.img_weather_after4)).setImageDrawable(getResources().getDrawable(R.drawable.partly_sunny_small));
                    break;
                case "阴"  :((ImageView) findViewById(R.id.img_weather_after4)).setImageDrawable(getResources().getDrawable(R.drawable.windy_small));
                    break;
            }

            //Update weekday
            int W0S = temperature.indexOf("week");
            int W0E = temperature.indexOf("fx" , W0S);
            int W1S = temperature.indexOf("week" , W0E);
            int W1E = temperature.indexOf("fx" , W1S);
            String week1 = temperature.substring(W1S + 7 , W1E - 3);
            switch(week1){
                case "星期一":((TextView)findViewById(R.id.tv_week1)).setText("MON");break;
                case "星期二":((TextView)findViewById(R.id.tv_week1)).setText("TUE");break;
                case "星期三":((TextView)findViewById(R.id.tv_week1)).setText("WED");break;
                case "星期四":((TextView)findViewById(R.id.tv_week1)).setText("THU");break;
                case "星期五":((TextView)findViewById(R.id.tv_week1)).setText("FRI");break;
                case "星期六":((TextView)findViewById(R.id.tv_week1)).setText("SAT");break;
                case "星期日":((TextView)findViewById(R.id.tv_week1)).setText("SUN");break;
            }

            int W2S = temperature.indexOf("week" , W1E);
            int W2E = temperature.indexOf("fx" , W2S);
            String week2 = temperature.substring(W2S + 7 , W2E - 3);
            switch(week2){
                case "星期一":((TextView)findViewById(R.id.tv_week2)).setText("MON");break;
                case "星期二":((TextView)findViewById(R.id.tv_week2)).setText("TUE");break;
                case "星期三":((TextView)findViewById(R.id.tv_week2)).setText("WED");break;
                case "星期四":((TextView)findViewById(R.id.tv_week2)).setText("THU");break;
                case "星期五":((TextView)findViewById(R.id.tv_week2)).setText("FRI");break;
                case "星期六":((TextView)findViewById(R.id.tv_week2)).setText("SAT");break;
                case "星期日":((TextView)findViewById(R.id.tv_week2)).setText("SUN");break;
            }

            int W3S = temperature.indexOf("week" , W2E);
            int W3E = temperature.indexOf("fx" , W3S);
            String week3 = temperature.substring(W3S + 7 , W3E - 3);
            switch(week3){
                case "星期一":((TextView)findViewById(R.id.tv_week3)).setText("MON");break;
                case "星期二":((TextView)findViewById(R.id.tv_week3)).setText("TUE");break;
                case "星期三":((TextView)findViewById(R.id.tv_week3)).setText("WED");break;
                case "星期四":((TextView)findViewById(R.id.tv_week3)).setText("THU");break;
                case "星期五":((TextView)findViewById(R.id.tv_week3)).setText("FRI");break;
                case "星期六":((TextView)findViewById(R.id.tv_week3)).setText("SAT");break;
                case "星期日":((TextView)findViewById(R.id.tv_week3)).setText("SUN");break;
            }

            int W4S = temperature.indexOf("week" , W3E);
            int W4E = temperature.indexOf("fx" , W4S);
            String week4 = temperature.substring(W4S + 7 , W4E - 3);
            switch(week4){
                case "星期一":((TextView)findViewById(R.id.tv_week4)).setText("MON");break;
                case "星期二":((TextView)findViewById(R.id.tv_week4)).setText("TUE");break;
                case "星期三":((TextView)findViewById(R.id.tv_week4)).setText("WED");break;
                case "星期四":((TextView)findViewById(R.id.tv_week4)).setText("THU");break;
                case "星期五":((TextView)findViewById(R.id.tv_week4)).setText("FRI");break;
                case "星期六":((TextView)findViewById(R.id.tv_week4)).setText("SAT");break;
                case "星期日":((TextView)findViewById(R.id.tv_week4)).setText("SUN");break;
            }

            int W5S = temperature.indexOf("week" , W4E);
            int W5E = temperature.indexOf("fx" , W5S);
            String week5 = temperature.substring(W5S + 7 , W5E - 3);
            switch(week5){
                case "星期一":((TextView)findViewById(R.id.tv_week5)).setText("MON");break;
                case "星期二":((TextView)findViewById(R.id.tv_week5)).setText("TUE");break;
                case "星期三":((TextView)findViewById(R.id.tv_week5)).setText("WED");break;
                case "星期四":((TextView)findViewById(R.id.tv_week5)).setText("THU");break;
                case "星期五":((TextView)findViewById(R.id.tv_week5)).setText("FRI");break;
                case "星期六":((TextView)findViewById(R.id.tv_week5)).setText("SAT");break;
                case "星期日":((TextView)findViewById(R.id.tv_week5)).setText("SUN");break;
            }

            Toast.makeText(MainActivity.this, "Successful weather acquisition!", Toast.LENGTH_SHORT).show();

            //((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperature);
        }
    }
}
