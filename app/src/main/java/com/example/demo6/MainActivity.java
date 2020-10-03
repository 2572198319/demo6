package com.example.demo6;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private DevinDrawPanle drawPanle;
    private float x1,x2,y1,y2;
    private int speed=12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawPanle = new DevinDrawPanle(this);
        setContentView(drawPanle);



        WindowManager windowManager=getWindowManager();
        final Display display=windowManager.getDefaultDisplay();
        int screenWidth=display.getWidth();
        int screenHeight=display.getHeight();
        drawPanle.currentX=screenWidth/2;
        drawPanle.currentY=screenHeight/2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_option, menu);
        //添加子菜单
        SubMenu mnLineColor = menu.addSubMenu("颜色");
        SubMenu mnLineStyle = menu.addSubMenu("线型");
        SubMenu mnShapeStyle = menu.addSubMenu("形状");
        //实例化子菜单
        getMenuInflater().inflate(R.menu.menu_paint_colors, mnLineColor);
        getMenuInflater().inflate(R.menu.menu_line_style, mnLineStyle);
        getMenuInflater().inflate(R.menu.menu_shape_style, mnShapeStyle);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                saveFile();
                break;
            //颜色选择
            case R.id.color_red:
                drawPanle.setPaintColor( Color.RED);
                break;
            case R.id.color_orange:
                drawPanle.setPaintColor("#FF6600");//有重载形式
                break;
            case R.id.color_yellow:
                drawPanle.setPaintColor(Color.YELLOW);
                break;
            case R.id.color_green:
                drawPanle.setPaintColor(Color.GREEN);
                break;
            case R.id.color_cyan:
                drawPanle.setPaintColor("#0055CC");
                break;
            case R.id.color_blue:
                drawPanle.setPaintColor(Color.BLUE);
                break;
            case R.id.color_purple:
                drawPanle.setPaintColor("#8800CC");
                break;
            case R.id.color_black:
                drawPanle.setPaintColor(Color.BLACK);
                break;
            case R.id.color_gray:
                drawPanle.setPaintColor(Color.GRAY);
                break;

            //设置线条粗细
            case R.id.drawline1:
                drawPanle.setPaintStrokeWidth(1);
                break;
            case R.id.drawline2:
                drawPanle.setPaintStrokeWidth(2);
                break;
            case R.id.drawline3:
                drawPanle.setPaintStrokeWidth(3);
                break;
            case R.id.drawline4:
                drawPanle.setPaintStrokeWidth(4);
                break;
            case R.id.drawline5:
                drawPanle.setPaintStrokeWidth(5);
                break;
            //设置形状
            case R.id.drawbezier:
                drawPanle.setShapeStyle(0);
                break;
            case R.id.drawline:
                drawPanle.setShapeStyle(1);
                break;
            case R.id.drawrect:
                drawPanle.setShapeStyle(2);
                break;
            case R.id.drawcircle:
                drawPanle.setShapeStyle(3);
                break;
            case R.id.drawoval:
                drawPanle.setShapeStyle(4);
                break;
            case R.id.drawroundrect:
                drawPanle.setShapeStyle(5);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            x1=event.getX();
            y1=event.getY();
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            x2=event.getX();
            y2=event.getY();
            if(y1-y2>50){
                //up
                drawPanle.currentY-=speed;
            }
            if(y2-y1>50){
                //down
                drawPanle.currentY+=speed;
            }
            if(x1-x2>50){
                //left
                drawPanle.currentX-=speed;
            }
            if(x2-x1>50){
                //right
               drawPanle.currentX+=speed;
            }
        }
        drawPanle.invalidate();
        return true;
    }


    /**
     * 保存文件
     */
    private void saveFile() {
        File saveFile = new File( Environment.getExternalStorageDirectory(), "mybmp2.png");
        drawPanle.saveBitmap(saveFile);
        Toast.makeText(this, "文件保存在：" + saveFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
    }
}

