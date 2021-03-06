package net.onest.moment.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.onest.moment.R;
import net.onest.moment.adapter.RoomAdapter;
import net.onest.moment.entity.Room;
import net.onest.moment.manager.ServerConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyFangJian extends AppCompatActivity{
    private TextView shop_name;
    private TextView shop_like;
    private TextView shop_star;
    private TextView shop_location;
    private ImageView shop_image;

    private ImageButton imageButton1;
    private OkHttpClient okHttpClient;

    private View view;
    private TextView textView;
    private List<Room> rooms;
    private Button button;
    private ImageButton imageButton2;

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    List<Room> list = (List<Room>) msg.obj;
                    RoomAdapter adapter =new RoomAdapter(MyFangJian.this,list, R.layout.myfangjian_item);
                    ListView listView1 =(ListView)findViewById(R.id.list1);
                    listView1.setAdapter(adapter);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfangjian);

        shop_name = findViewById(R.id.shop_name);
        shop_like = findViewById(R.id.shop_like);
        shop_location = findViewById(R.id.shop_location);
        shop_star = findViewById(R.id.shop_star);
        shop_image = findViewById(R.id.shop_image);

        imageButton1 = findViewById(R.id.kefu1);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                //Bundle bundle =  intent.getBundleExtra("bundle");
                //Bitmap bitmap = bundle.getParcelable("bitmap");
                //Bitmap bitmap = intent.getParcelableExtra("bitmap");
                //Log.e("onClick:bitmap ", bitmap.toString());

                Intent intent1 = new Intent();
                intent1.setClass(MyFangJian.this,ChatDetailActivity.class);

                //Bundle bundle1 = new Bundle();
                //bundle.putParcelable("bitmap", bitmap);
                //intent1.putExtra("bitmap", bitmap);
                startActivity(intent1);
            }
        });

        imageButton2 =findViewById(R.id.collection);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = getIntent();
                    String shop_id = intent.getStringExtra("shop_id");
                    String user_id = "2"; //由其他页面传过来,这里暂时用固定值
                    Log.e("onClick: shop_id", shop_id);
                    Log.e( "onClick: user_id", user_id);
                    Request request = new Request.Builder()
                            .url(ServerConfig.SERVER_HOME + "shop/addCollection?user_id=" + user_id + "&shop_id=" + shop_id)
                            .get()
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        /*ArrayList<Integer> images = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        title.add("1");
        title.add("2");
        title.add("3");
        images.add(R.drawable.aaa);
        images.add(R.drawable.aaa);
        images.add(R.drawable.aaa);

        banner = findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerTitles(title);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.start();

        banner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 60);
            }
        });

        banner.setClipToOutline(true);*/

        Intent intent = getIntent();
        String shopName = intent.getStringExtra("shop_name");
        String shopLike = intent.getStringExtra("shop_likes");
        String shopStar = intent.getStringExtra("shop_stars");
        String shopLocation = intent.getStringExtra("shop_location");
        Log.e("hhhhhhh",shopName);
        shop_name.setText(shopName);
        shop_like.setText(shopLike+"");
        shop_star.setText(shopStar+"");
        shop_location.setText(shopLocation);
        //shop_image.setImageBitmap(bitmap);

        button = findViewById(R.id.yuyue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                final String shop_id = intent.getStringExtra("shop_id");
                final String user_id = "2"; //由其他页面传过来,这里暂时用固定值
                final int stars = intent.getIntExtra("shop_stars",0) + 1;
                Log.e( "onClick: stars", stars + "");

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(ServerConfig.SERVER_HOME + "shop/addAppointment?user_id=" + user_id + "&shop_id=" + shop_id);
                            InputStream is = url.openStream();
                            URL url1 = new URL(ServerConfig.SERVER_HOME + "shop/updateLikes?shop_id=" + shop_id + "&newStars=" + stars);
                            InputStream is1 = url1.openStream();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                Intent intent1 = new Intent();
                intent1.setClass(MyFangJian.this,SeatActivity.class);
                startActivity(intent1);
            }
        });
        initOkHttpClient();
        simpStringParamPostRequest();
    }

    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient();
    }


    private void simpStringParamPostRequest() {
        new Thread() {
            @Override
            public void run() {
                Intent intent = getIntent();
                String id = intent.getStringExtra("shop_id");
                Log.e("run", id + "");
                MediaType type = MediaType.parse("text/plain");
                //1.创建RequestBody对象
                //2.创建请求对象
                Request request = new Request.Builder()
                        .url(ServerConfig.SERVER_HOME + "room/roomList?shop_id=" + id)
                        .get()
                        .build();
                //3.创建CALL对象
                Call call = okHttpClient.newCall(request);
                //4.提交请求并获取响应
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("sq", "请求失败");
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String result = response.body().string();
                        Log.e("onResponse", result);
                        Gson gson = new Gson();
                        List<Room> rooms =  gson.fromJson(result,new TypeToken<List<Room>>(){}.getType());
                        Message msg = myHandler.obtainMessage();
                        msg.what = 1;
                        msg.obj = rooms;
                        myHandler.sendMessage(msg);
                    }
                });
            }
        }.start();

    }

}
