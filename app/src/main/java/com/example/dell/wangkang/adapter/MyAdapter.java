package com.example.dell.wangkang.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dell.wangkang.R;
import com.example.dell.wangkang.bean.CarBean;
import com.example.dell.wangkang.bean.DeleteBean;
import com.example.dell.wangkang.presenter.MyPresenter;
import com.example.dell.wangkang.presenter.MyPresenter2;
import com.example.dell.wangkang.view.CustomJiajian;
import com.example.dell.wangkang.view.MyViewCallBack;
import com.example.dell.wangkang.view.MyViewCallBack2;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.MyViewHolder> implements MyViewCallBack2, MyViewCallBack {
    MyPresenter myPresenter;
    private List<CarBean.DataBean.ListBean> list;
    private Map<String, String> map = new HashMap<>();
    MyPresenter2 myPresenter2;
    Context context;

    public MyAdapter(Context context) {
        this.context = context;
        myPresenter = new MyPresenter(this);
        myPresenter2 = new MyPresenter2(this);
        Fresco.initialize(context);
    }

    public void add(CarBean carBean) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (carBean != null) {
            for (CarBean.DataBean shop : carBean.getData()) {
                map.put(shop.getSellerid(), shop.getSellerName());
                for (int i = 0; i < shop.getList().size(); i++) {
                    list.add(shop.getList().get(i));
                }
            }
            setFirst(list);
        }
        notifyDataSetChanged();
    }

    private void setFirst(List<CarBean.DataBean.ListBean> list) {
        if (list.size() > 0) {
            list.get(0).setIsFirst(1);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getSellerid() == list.get(i - 1).getSellerid()) {

                    list.get(i).setIsFirst(2);
                } else {
                    list.get(i).setIsFirst(1);

                    if (list.get(i).isItem_check() == true) {
                        list.get(i).setShop_check(list.get(i).isItem_check());
                    }
                }
            }
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recy_cart_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(list.get(position).getIsFirst()==1){

            holder.shopCheckbox.setVisibility(View.VISIBLE);
            holder.shopName.setVisibility(View.VISIBLE);

            holder.shopCheckbox.setChecked(list.get(position).isShop_check());
            holder.shopName.setText(map.get(String.valueOf(list.get(position).getSellerid()))+" ＞");
        }else{
            holder.shopName.setVisibility(View.GONE);
            holder.shopCheckbox.setVisibility(View.GONE);
        }


        String[] split = list.get(position).getImages().split("\\|");

        holder.itemFace.setImageURI(Uri.parse(split[0]));

        holder.itemCheckbox.setChecked(list.get(position).isItem_check());
        holder.itemName.setText(list.get(position).getTitle());
        holder.itemPrice.setText(list.get(position).getPrice()+"");

        holder.customJiajian.setEditText(list.get(position).getNum());

        holder.itemBianji.setTag(1);

        holder.itemBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag= (int) holder.itemBianji.getTag();

                if(tag==1) {
                    //加减号显示
                    holder.itemBianji.setText("完成");
                    holder.customJiajian.setVisibility(View.VISIBLE);
                    //商品的名称隐藏
                    holder.itemName.setVisibility(View.GONE);
                    holder.itemYansechima.setVisibility(View.VISIBLE);
                    holder.itemPrice.setVisibility(View.GONE);
                    holder.itemDelete.setVisibility(View.VISIBLE);
                    holder.itemBianji.setTag(2);
                }else{

                    holder.itemBianji.setText("编辑");
                    holder.customJiajian.setVisibility(View.GONE);

                    holder.itemName.setVisibility(View.VISIBLE);
                    holder.itemYansechima.setVisibility(View.GONE);
                    holder.itemPrice.setVisibility(View.VISIBLE);
                    holder.itemDelete.setVisibility(View.GONE);
                    holder.itemBianji.setTag(1);
                }
            }
        });

        holder.shopCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.get(position).setShop_check(holder.shopCheckbox.isChecked());

                for (int i=0;i<list.size();i++){

                    if(list.get(position).getSellerid()==list.get(i).getSellerid()){

                        list.get(i).setItem_check(holder.shopCheckbox.isChecked());
                    }
                }

                notifyDataSetChanged();

                sum(list);
            }
        });


        holder.itemCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.get(position).setItem_check(holder.itemCheckbox.isChecked());


                for (int i=0;i<list.size();i++){
                    for (int j=0;j<list.size();j++){

                        if(list.get(i).getSellerid()==list.get(j).getSellerid() && !list.get(j).isItem_check()){

                            list.get(i).setShop_check(false);
                            break;
                        }else{

                            list.get(i).setShop_check(true);
                        }
                    }
                }


                notifyDataSetChanged();

                sum(list);
            }
        });


        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);

                setFirst(list);


                sum(list);
                notifyDataSetChanged();
            }
        });


        holder.customJiajian.setCustomListener(new CustomJiajian.CustomListener() {
            @Override
            public void jiajian(int count) {

                list.get(position).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }

            @Override

            public void shuRuZhi(int count) {
                list.get(position).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }
        });
    }


    public void shanChu() {

        final List<Integer> delete_listid = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            if(list.get(i).isItem_check()){

                delete_listid.add(list.get(i).getPid());
            }
        }
        if (delete_listid.size()==0){

            Toast.makeText(context, "请选中至少一个商品后再删除", Toast.LENGTH_SHORT).show();
            return;
        }

        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("操作提示");
        dialog.setMessage("你确定要删除这"+delete_listid.size()+"个商品?");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String a  = "";
                for(int j=0;j<delete_listid.size();j++) {


                    Integer integer = delete_listid.get(j);
                    String pid = String.valueOf(integer);
                    myPresenter2.delete(pid);

                }


            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create().show();
    }


    public void quanXuan(boolean checked) {
        for (int i=0;i<list.size();i++){
            list.get(i).setShop_check(checked);
            list.get(i).setItem_check(checked);

        }
        notifyDataSetChanged();
        sum(list);
    }

    private void sum(List<CarBean.DataBean.ListBean> list) {
        int totalNum = 0;
        float totalMoney = 0.0f;
        boolean allCheck = true;
        for (int i=0;i<list.size();i++){
            if (list.get(i).isItem_check()){
                totalNum += list.get(i).getNum();
                totalMoney += list.get(i).getNum() * list.get(i).getPrice();
            }else{

                allCheck = false;
            }
        }

        updateListener.setTotal(totalMoney+"",totalNum+"",allCheck);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    @Override
    public void success(CarBean carBean) {

            list.clear();
            add(carBean);



    }

    @Override
    public void success(DeleteBean deleteBean) {
        myPresenter.select();
    }

    @Override
    public void failure() {
        System.out.println("网不好");
        Toast.makeText(context, "adapter网有点慢", Toast.LENGTH_SHORT).show();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.shop_checkbox)
        CheckBox shopCheckbox;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.item_bianji)
        TextView itemBianji;
        @BindView(R.id.item_checkbox)
        CheckBox itemCheckbox;
        @BindView(R.id.item_face)
        SimpleDraweeView itemFace;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.custom_jiajian)
        CustomJiajian customJiajian;
        @BindView(R.id.item_yansechima)
        TextView itemYansechima;
        @BindView(R.id.item_delete)
        TextView itemDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    UpdateListener updateListener;
    public void setUpdateListener(UpdateListener updateListener){
        this.updateListener = updateListener;
    }

    public interface UpdateListener{
        public void setTotal(String total, String num, boolean allCheck);
    }
}
