package datnguyen.lab.ktralan2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    GridView gridView;
    CardAdapter cardAdapter;
    List<CardItem> lc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        lc = getListData();
        cardAdapter = new CardAdapter(this, lc);
        gridView.setAdapter(cardAdapter);
        registerForContextMenu(this.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CardItem cardItem = (CardItem) getListData().get(position);
                Intent intent = new Intent(MainActivity.this, GridContext.class);
                intent.putExtra("noidung", cardItem.getName());
                startActivity(intent);
            }
        });

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpaper);
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("LazaMall"));
        tabLayout.addTab(tabLayout.newTab().setText("Free Shipping"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private List<CardItem> getListData() {
        List<CardItem> cardItemList = new ArrayList<>();
        cardItemList.add(new CardItem(R.drawable.honghanh, "Điện thoại Nokia", "100.000dVND"));
        cardItemList.add(new CardItem(R.drawable.honghanh, "Điện thoại Nokia", "100.000dVND"));
        cardItemList.add(new CardItem(R.drawable.honghanh, "Điện thoại Nokia", "100.000dVND"));
        cardItemList.add(new CardItem(R.drawable.honghanh, "Điện thoại Nokia", "100.000dVND"));
        cardItemList.add(new CardItem(R.drawable.honghanh, "Điện thoại Nokia", "100.000dVND"));

        return cardItemList;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Chọn hành động");
        menu.add(0, 1, 0, "Xóa");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final CardItem cardItemSelected = (CardItem) this.getListData().get(info.position);
        if (item.getItemId() == 1) {
            // Ask before deleting.
            new AlertDialog.Builder(this)
                    .setMessage(cardItemSelected.getName() + ". Bạn có muốn xoá?")
                    .setCancelable(false)
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteItem(info.position);
                        }
                    })
                    .setNegativeButton("Không", null)
                    .show();
        } else
            return false;
        return true;
    }

    private void deleteItem(int item) {
        lc.remove(item);
        cardAdapter.notifyDataSetChanged();
    }
}