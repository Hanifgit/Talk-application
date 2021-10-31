package com.example.chatspatial;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupMessageAdapter extends RecyclerView.Adapter<GroupMessageAdapter.GroupMessageViewHolder> {
    private List<GroupMessages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    public GroupMessageAdapter (List<GroupMessages> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
    }

    @NonNull
    @Override
    public GroupMessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_messages_layout, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();

        return new GroupMessageAdapter.GroupMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageViewHolder holder, int position) {
        String messageSenderId = mAuth.getCurrentUser().getUid();
        GroupMessages messages = userMessagesList.get(position);

        String fromUserID = messages.getFrom();

        String fromMessageType = messages.getType();

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChild("image"))
                {
                    String receiverImage = dataSnapshot.child("image").getValue().toString();
                    String receiverName = dataSnapshot.child("name").getValue().toString();

                    Picasso.get().load(receiverImage).placeholder(R.drawable.profile_image).into(holder.receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        holder.receiverMessageText.setVisibility(View.GONE);
        holder.receiverMessageName.setVisibility(View.GONE);
        holder.receiverProfileImage.setVisibility(View.GONE);
        holder.senderMessageText.setVisibility(View.GONE);
        holder.messageSenderPicture.setVisibility(View.GONE);
        holder.messageReceiverPicture.setVisibility(View.GONE);
        holder.receiverMessageTextDate.setVisibility(View.GONE);
        holder.senderMessageTextDate.setVisibility(View.GONE);
        holder.messageReceiverImageDate.setVisibility(View.GONE);
        holder.messageSenderImageDate.setVisibility(View.GONE);



        if (fromMessageType.equals("text"))
        {
            if (fromUserID.equals(messageSenderId))
            {
                holder.senderMessageText.setVisibility(View.VISIBLE);
                holder.senderMessageTextDate.setVisibility(View.VISIBLE);

                holder.senderMessageText.setBackgroundResource(R.drawable.sender_messages_layout);
                holder.senderMessageText.setTextColor(Color.BLACK);
                holder.senderMessageText.setText(messages.getMessage());
                holder.senderMessageTextDate.setText(messages.getTime() + " - " + messages.getDate()+"\n");
            }
            else
            {
                holder.receiverMessageName.setVisibility(View.VISIBLE);
                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.receiverMessageText.setVisibility(View.VISIBLE);
                holder.receiverMessageTextDate.setVisibility(View.VISIBLE);

                holder.receiverMessageText.setBackgroundResource(R.drawable.receiver_messages_layout);
                holder.receiverMessageText.setTextColor(Color.BLACK);
                holder.receiverMessageText.setText(messages.getMessage());
                holder.receiverMessageName.setText(messages.getName());
                holder.receiverMessageTextDate.setText(messages.getTime() + " - " + messages.getDate()+"\n\n");
            }
        }
        else if (fromMessageType.equals("image")){
            if (fromUserID.equals(messageSenderId))
            {
                holder.messageSenderPicture.setVisibility(View.VISIBLE);
                holder.messageSenderImageDate.setVisibility(View.VISIBLE);
                holder.messageSenderImageDate.setText(messages.getTime() + " - " + messages.getDate()+"\n");
                Picasso.get().load(messages.getMessage()).into(holder.messageSenderPicture);
            }
            else
            {
                holder.receiverMessageName.setVisibility(View.VISIBLE);
                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.messageReceiverPicture.setVisibility(View.VISIBLE);
                holder.receiverMessageName.setText(messages.getName());
                holder.messageReceiverImageDate.setVisibility(View.VISIBLE);
                holder.messageReceiverImageDate.setText(messages.getTime() + " - " + messages.getDate()+"\n");
                Picasso.get().load(messages.getMessage()).into(holder.messageReceiverPicture);
            }
        }else{
            if (fromUserID.equals(messageSenderId))
            {
                holder.messageSenderPicture.setVisibility(View.VISIBLE);
                holder.messageSenderPicture.setBackgroundResource(R.drawable.file);
                holder.messageSenderImageDate.setVisibility(View.VISIBLE);
                holder.messageSenderImageDate.setText(messages.getTime() + " - " + messages.getDate()+"\n");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userMessagesList.get(position).getMessage()));
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
            }
            else
            {
                holder.receiverMessageName.setVisibility(View.VISIBLE);
                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.messageReceiverPicture.setVisibility(View.VISIBLE);
                holder.messageReceiverPicture.setBackgroundResource(R.drawable.file);
                holder.receiverMessageName.setText(messages.getName());
                holder.messageReceiverImageDate.setVisibility(View.VISIBLE);
                holder.messageReceiverImageDate.setText(messages.getTime() + " - " + messages.getDate()+"\n");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userMessagesList.get(position).getMessage()));
                        holder.itemView.getContext().startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

    public class GroupMessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView receiverMessageName,senderMessageText, receiverMessageText,senderMessageTextDate
                ,receiverMessageTextDate,messageReceiverImageDate,messageSenderImageDate;
        public CircleImageView receiverProfileImage;
        public ImageView messageSenderPicture, messageReceiverPicture;


        public GroupMessageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.sender_messsage_text);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
            receiverProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image);
            messageReceiverPicture = itemView.findViewById(R.id.message_receiver_image_view);
            messageSenderPicture = itemView.findViewById(R.id.message_sender_image_view);
            senderMessageTextDate = itemView.findViewById(R.id.sender_messsage_text_date);
            receiverMessageTextDate = itemView.findViewById(R.id.receiver_message_text_date);
            receiverMessageName = itemView.findViewById(R.id.receiver_message_name);
            messageReceiverImageDate = itemView.findViewById(R.id.message_receiver_image_date);
            messageSenderImageDate = itemView.findViewById(R.id.message_sender_image_date);
        }
    }
}
