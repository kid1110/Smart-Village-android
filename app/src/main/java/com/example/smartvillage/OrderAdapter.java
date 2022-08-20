package com.example.smartvillage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartvillage.entity.Order;
import com.example.smartvillage.response.BaseResponse;
import com.example.smartvillage.result.Status;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;



import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context orderContext;
    private List<Order> orderCard;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(orderContext).inflate(R.layout.order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderCard.get(position);
        holder.address.setText("地点: "+order.getAddress());
        holder.date.setText("时间： "+order.getOrderDate());
        holder.setOid(order.getOid());
        holder.setUid(order.getUid());
        holder.setTypeId(order.getTypeId());
        holder.setSdate(order.getOrderDate());
        holder.setSaddress(order.getAddress());
    }

    @Override
    public int getItemCount() {
        return orderCard.size();
    }

    public OrderAdapter(Context orderContext, List<Order> orderCard) {
        this.orderContext = orderContext;
        this.orderCard = orderCard;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        MaterialCardView orderView;
        TextView address;
        TextView date;
        String sdate;
        String saddress;
        MaterialAlertDialogBuilder materialAlertDialogBuilder;
        SharedPreferences sharedPreferences;
        String token;
        int oid;
        int uid;
        int typeId;
        MaterialButton cancel,update;
        public ViewHolder(View view) {
            super(view);
            orderView = (MaterialCardView) view;
            address = view.findViewById(R.id.address_order);
            date = view.findViewById(R.id.order_time);
            cancel = view.findViewById(R.id.delete_Order);
            update = view.findViewById(R.id.update_order);
            sharedPreferences = view.getContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
            token = sharedPreferences.getString("user",null);
            materialAlertDialogBuilder = new MaterialAlertDialogBuilder(view.getContext());
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialAlertDialogBuilder.setTitle(view.getContext().getResources().getString(R.string.delete_order_confirm)).setMessage(R.string.delete_order_message).setNegativeButton(view.getContext().getResources().getString(R.string.delete_card_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton(view.getContext().getString(R.string.delete_card_accept), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Thread(()->{
                                Log.i("TAG", "onClick: "+oid);
                                BaseResponse baseResponse = OrderHelper.deleteOrder(token,oid);
                                if(baseResponse.getCode() == Status.Success.getCode()){
                                    Looper.prepare();
                                    Toast.makeText(view.getContext(),"取消预定成功",Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    Activity ac = (Activity)view.getContext();
                                    ac.finish();
                                    Looper.loop();
                                }else{
                                    Looper.prepare();
                                    Toast.makeText(view.getContext(), baseResponse.getMsg(),Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    Looper.loop();
                                }
                            }).start();
                        }
                    }).create();
                    materialAlertDialogBuilder.show();
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialAlertDialogBuilder.setTitle(view.getContext().getString(R.string.update_order_confirm)).setMessage(view.getContext().getString(R.string.update_order_message)).setNegativeButton(view.getContext().getString(R.string.delete_card_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton(view.getContext().getString(R.string.delete_card_accept), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Order push = new Order(oid,uid,saddress,typeId,sdate);
                            Intent intent = new Intent(v.getContext(),CheckOrderActivity.class);
                            intent.putExtra("Order",push);
                            v.getContext().startActivity(intent);
                        }
                    }).create();
                    materialAlertDialogBuilder.show();
                }
            });
        }

        public MaterialCardView getOrderView() {
            return orderView;
        }

        public void setOrderView(MaterialCardView orderView) {
            this.orderView = orderView;
        }

        public TextView getAddress() {
            return address;
        }

        public void setAddress(TextView address) {
            this.address = address;
        }

        public TextView getDate() {
            return date;
        }

        public void setDate(TextView date) {
            this.date = date;
        }

        public String getSdate() {
            return sdate;
        }

        public void setSdate(String sdate) {
            this.sdate = sdate;
        }

        public String getSaddress() {
            return saddress;
        }

        public void setSaddress(String saddress) {
            this.saddress = saddress;
        }

        public MaterialAlertDialogBuilder getMaterialAlertDialogBuilder() {
            return materialAlertDialogBuilder;
        }

        public void setMaterialAlertDialogBuilder(MaterialAlertDialogBuilder materialAlertDialogBuilder) {
            this.materialAlertDialogBuilder = materialAlertDialogBuilder;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public MaterialButton getCancel() {
            return cancel;
        }

        public void setCancel(MaterialButton cancel) {
            this.cancel = cancel;
        }

        public MaterialButton getUpdate() {
            return update;
        }

        public void setUpdate(MaterialButton update) {
            this.update = update;
        }
    }
}
