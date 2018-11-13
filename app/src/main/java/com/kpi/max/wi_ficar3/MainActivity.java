package com.kpi.max.wi_ficar3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.Snackbar;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainActivity fMainActive;

    public final static String PREF_IP = "PREF_IP_ADDRESS";
    public final static String PREF_PORT = "PREF_PORT_NUMBER";
    // объявление кнопок и текстовых полей ввода
    private Button buttonPin11, buttonPin12, buttonPin13;
    private EditText editTextIPAddress, editTextPortNumber;
    // общие объекты параметров, используемые для сохранения IP адреса и порта, чтобы
    // пользователь не вводил их в следующий раз, когда он открывает приложение
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("HTTP_HELPER_PREFS", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        FragmentTransaction fragT = getFragmentManager().beginTransaction();

        // назначить кнопки
        buttonPin11 = (Button) findViewById(R.id.buttonPin11);
        buttonPin12 = (Button) findViewById(R.id.buttonPin12);
        buttonPin13 = (Button) findViewById(R.id.buttonPin13);

        // назначить поля ввода
        editTextIPAddress = (EditText) findViewById(R.id.editTextIPAddress);
        editTextPortNumber = (EditText) findViewById(R.id.editTextPortNumber);

        // назначить слушателя кнопок (этот класс)
        buttonPin11.setOnClickListener(this);
        buttonPin12.setOnClickListener(this);
        buttonPin13.setOnClickListener(this);

        // получить IP адрес и номер порта из последнего раза, когда пользователь использовал
        // приложение, или поместить пустую строку "", если это первый раз
        editTextIPAddress.setText(sharedPreferences.getString(PREF_IP, ""));
        editTextPortNumber.setText(sharedPreferences.getString(PREF_PORT, ""));

        fMainActive = new MainActivity();
/*
        fragT.add(R.id.container, fMainActive);
        fragT.commit();
*/

    }


    @Override
    public void onClick(View view) {

        // номер вывода
        String parameterValue = "";
        // получить ip адрес
        String ipAddress = editTextIPAddress.getText().toString().trim();
        // получить номер порта
        String portNumber = editTextPortNumber.getText().toString().trim();


        // сохранить IP адрес и номер порта для следующего использования приложения
        editor.putString(PREF_IP, ipAddress);    // установить значение ip адреса для сохранения
        editor.putString(PREF_PORT, portNumber); // установить номер порта для сохранения
        editor.commit(); // сохранить IP и PORT

        // получить номер порта от кнопки, которая была нажата
        if (view.getId() == buttonPin11.getId()) {
            parameterValue = "11";
        } else if (view.getId() == buttonPin12.getId()) {
            parameterValue = "12";
        } else {
            parameterValue = "13";
        }


        // выполнить HTTP запрос
        if (ipAddress.length() > 0 && portNumber.length() > 0) {
            new HttpRequestAsyncTask(
                    view.getContext(), parameterValue, ipAddress, portNumber, "pin"
            ).execute();
        }
    }

    /**
     * Description: Послать HTTP Get запрос на указанные ip адрес и порт.
     * Также послать параметр "parameterName" со значением "parameterValue".
     *
     * @param parameterValue номер порта, у которого необходимо изменить состояние
     * @param ipAddress      ip адрес, на который необходимо послать запрос
     * @param portNumber     номер порта ip адреса
     * @param parameterName
     * @return Текст ответа с ip адреса или сообщение ERROR, если не получилось получить ответ
     */
    public String sendRequest(String parameterValue, String ipAddress, String portNumber, String parameterName) {
        String serverResponse = "ERROR";

        try {

            HttpClient httpclient = new DefaultHttpClient(); // создать HTTP клиента
            // установить URL, например, http://myIpaddress:myport/?pin=13 (например, переключить вывод 13)
            URI website = new URI("http://" + ipAddress + ":" + portNumber + "/?" + parameterName + "=" + parameterValue);
            HttpGet getRequest = new HttpGet(); // создать объект HTTP GET
            getRequest.setURI(website);         // установить URL для GET запроса
            HttpResponse response = httpclient.execute(getRequest); // выполнить запрос
            // получить ответ сервера с заданным ip адресом
            InputStream content = null;
            content = response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    content
            ));
            serverResponse = in.readLine();
            // Закрыть соединение
            content.close();
        } catch (ClientProtocolException e) {
            // ошибка HTTP
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            // ошибка ввода/вывода
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // ошибка синтаксиса URL
            serverResponse = e.getMessage();
            e.printStackTrace();
        }
        // вернуть текст отклика сервера
        return serverResponse;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Snackbar.make(findViewById(android.R.id.content), "Author Max Alpert. KPI 2018, All rights reserved",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * AsyncTask необходим для выполнения HTTP запроса в фоне, чтобы они не блокировали
     * пользовательский интерфейс.
     */
    private class HttpRequestAsyncTask extends AsyncTask<Void, Void, Void> {

        // объявить необходимые переменные
        private String requestReply, ipAddress, portNumber;
        private Context context;
        private AlertDialog alertDialog;
        private String parameter;
        private String parameterValue;

        /**
         * Description: Конструктор класса asyncTask. Назначить значения, используемые в других методах.
         *
         * @param context        контекст приложения, необходим для создания диалога
         * @param parameterValue номер вывода для переключения
         * @param ipAddress      ip адрес, на который необходимо послать запрос
         * @param portNumber     номер порта ip адреса
         */
        public HttpRequestAsyncTask(Context context, String parameterValue, String ipAddress, String portNumber, String parameter) {
            this.context = context;

            alertDialog = new AlertDialog.Builder(this.context)
                    .setTitle("HTTP Response From IP Address:")
                    .setCancelable(true)
                    .create();

            this.ipAddress = ipAddress;
            this.parameterValue = parameterValue;
            this.portNumber = portNumber;
            this.parameter = parameter;
        }

        /**
         * Name: doInBackground
         * Description: Отправляет запрос на ip адрес
         *
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {
            alertDialog.setMessage("Data sent, waiting for reply from server...");
            if (!alertDialog.isShowing()) {
                alertDialog.show();
            }
            requestReply = sendRequest(parameterValue, ipAddress, portNumber, parameter);
            return null;
        }

        /**
         * Name: onPostExecute
         * Description: Данная функция выполняется после возвращения ответа на HTTP запрос на ip адрес.
         * Функция устанавливает сообщение диалога с текстом ответа от сервера и отображает диалог,
         * если он уже не показан (в случае, если он был закрыт случайно);
         *
         * @param aVoid void параметр
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            alertDialog.setMessage(requestReply);
            if (!alertDialog.isShowing()) {
                alertDialog.show(); // показать диалог
            }
        }

        /**
         * Name: onPreExecute
         * Description: Данная функция выполняется перед отправкой HTTP запроса на ip адрес.
         * Функция установит сообщение диалога и отобразит диалоговое окно.
         */
        @Override
        protected void onPreExecute() {
            alertDialog.setMessage("Sending data to server, please wait...");
            if (!alertDialog.isShowing()) {
                alertDialog.show();
            }
        }

    }
}
