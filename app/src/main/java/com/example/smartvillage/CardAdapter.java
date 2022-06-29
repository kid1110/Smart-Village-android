package com.example.smartvillage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartvillage.entity.Card;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private Context cardContext;
    private List<Card> dashCard;


    public class ViewHolder extends RecyclerView.ViewHolder{
        MaterialCardView cardView;
        ImageView imageView;
        TextView title;
        TextView author;
        TextView shotInfo;
        String content;
        String timeStamp;
        String imageUrl;
        int cid;

        public ViewHolder(View view){
            super(view);
            cardView = (MaterialCardView) view;
            imageView = view.findViewById(R.id.cardImage);
            title = view.findViewById(R.id.cardTitle);
            author = view.findViewById(R.id.cardAuthor);
            shotInfo = view.findViewById(R.id.cardContent);

            //绑定点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Card card = new Card(cid,imageUrl,title.getText().toString(),author.getText().toString(),content,"",timeStamp);
                    Intent intent = new Intent(v.getContext(),ShowCardActivity .class);
                    intent.putExtra("showCard",card);
                    v.getContext().startActivity(intent);
                }
            });
        }

        public MaterialCardView getCardView() {
            return cardView;
        }

        public void setCardView(MaterialCardView cardView) {
            this.cardView = cardView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getAuthor() {
            return author;
        }

        public void setAuthor(TextView author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public CardAdapter(Context context,List<Card> cardList){
        this.dashCard = cardList;
        this.cardContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(cardContext).inflate(R.layout.dashboard_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        Card card = dashCard.get(position);
        holder.title.setText(card.getTitle());
        holder.author.setText(card.getAuthor());
        holder.shotInfo.setText(card.getShortInfo());
        holder.setContent(card.getContent());
        holder.setTimeStamp(card.getTimeStamp());
        holder.setCid(card.getCid());
        holder.setImageUrl(card.getImage());


        if(card.getImage()!= null && !card.getImage().isEmpty()){
            Glide.with(cardContext).load("http://112.74.93.38:8899/cardImages/"+card.getImage()).centerCrop().into(holder.imageView);
        }else{
            Glide.with(cardContext).load(R.drawable.test).centerCrop().into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return dashCard.size();
    }

    public  List<Card> getDashCard() {
        return dashCard;
    }


}
