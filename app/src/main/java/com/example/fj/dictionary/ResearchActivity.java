package com.example.fj.dictionary;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databaseHelper.DatabaseStatic;
import com.example.databaseHelper.MyHelper;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ResearchActivity extends AppCompatActivity {
    private SQLiteDatabase database = null;
    private Button[] button=new Button[3];
    private EditText editText;
    private TextView[] textView=new TextView[5];
    private int sum=9;
    private String WORD="";
    private String ACCEPTATION="";
    private String ORIG="";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);
        button[0]=(Button)findViewById(R.id.button1);
        button[1]=(Button)findViewById(R.id.button2);
        button[2]=(Button)findViewById(R.id.button3);
        editText=(EditText)findViewById(R.id.edit_input);
        textView[0]=(TextView)findViewById(R.id.output_text1);
        textView[1]=(TextView)findViewById(R.id.output_text2);
        textView[2]=(TextView)findViewById(R.id.output_text3);
        textView[3]=(TextView)findViewById(R.id.output_text4);
        textView[4]=(TextView)findViewById(R.id.output_text5);
        intent=getIntent();
        sum=intent.getIntExtra("Sum",0);
        button[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WORD=editText.getText().toString();
                wllj xx=new wllj(WORD);
                xx.start();
            }
        });
        button[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertDatabase();
            }
        });
        button[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Sum1",sum);
                setResult(0,intent);
                finish();
            }
        });
    }
    private void insertDatabase() // 向数据库中插入新数据
    {

        database = DatabaseStatic.myHelper.getWritableDatabase();

        ContentValues cV = new ContentValues();
        cV.put(DatabaseStatic.WORD, textView[0].getText().toString());
        cV.put(DatabaseStatic.MEANING, textView[2].getText().toString());;
        cV.put(DatabaseStatic.SAMPLE, textView[3].getText().toString());
        Log.i("123","成功插入单词："+textView[0].getText().toString());
  /*
   * 这个方法是留给不熟悉SQL语句的小伙伴用的，Android把
   * SQLite的插入语句封装了起来，
   * 通过 ContentValues 类的对象来保存数据库中的数据，
   * 于HashMap
   */
        database.insert(DatabaseStatic.TABLE_NAME, null, cV);

  /*
   * 对应的SQL语句：
   * database.execSQL("insert into " + DatabaseStatic.TABLENAME + " values(?, ?, ?, ?)",
   * new Object[]{"C Language", ++bookSum, "zhidian", 42.6});
   * 或者是这个：
   * database.execSQL("insert into " + DatabaseStatic.TABLENAME + "(" +
   *  DatabaseStatic.BOOKNAME + ", " + DatabaseStatic.ID + ", " +
   *  DatabaseStatic.AUTHOR + ", " + DatabaseStatic.PRICE +
   *  ") values(?, ?, ?, ?)", new Object[]{"C Language", ++bookSum, "zhidian", 42.6});
   * 这里将 ？ 号理解成一个C语言里面的占位符，然后通过 Object[] 数组中的内容补全，下同
   * 参数中的 Object[] 数组是一个通用的数组，里面的数据可以转换为任意类型的数据，通过这个完成不同数据类型变量之间的储存
  */

        Toast.makeText(this, "插入数据成功", Toast.LENGTH_SHORT).show();
    }

    class wllj extends Thread
    {
        String word;
        wllj(String word)
        {
            this.word=word;
        }
        public void run()
        {
            try {
                URL uri =new URL("http://dict-co.iciba.com/api/dictionary.php?w="+word+"&key=27ED27166918C664C90438B501855561"); // 创建URL对象
                HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
                conn.setConnectTimeout(10000); // 设置相应超时
                conn.setRequestMethod("GET");
                int statusCode = conn.getResponseCode();
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    Log.i("123","Http错误码：" + statusCode);
                }
                // 读取服务器的数据
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }

                String text = builder.toString();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputStream iStream=new ByteArrayInputStream(text.getBytes());
                Document document = db.parse(iStream);

                NodeList acceptation=document.getElementsByTagName("acceptation");
                NodeList ps=document.getElementsByTagName("ps");
                NodeList orig=document.getElementsByTagName("orig");
                NodeList trans=document.getElementsByTagName("trans");
                textView[0].setText(word);
                textView[1].setText("英 ["+ps.item(0).getFirstChild().getNodeValue()+"]");
                ACCEPTATION=acceptation.item(0).getFirstChild().getNodeValue();
                textView[2].setText(ACCEPTATION);
                ORIG=orig.item(0).getFirstChild().getNodeValue();
                textView[3].setText(ORIG);
                textView[4].setText(trans.item(0).getFirstChild().getNodeValue());
                br.close(); // 关闭数据流
                is.close(); // 关闭数据流
                conn.disconnect(); // 断开连接
            }
            catch (java.io.IOException e)
            {

            }
            catch (javax.xml.parsers.ParserConfigurationException e)
            {

            }
            catch(org.xml.sax.SAXException e)
            {

            }

        }
    }
}
