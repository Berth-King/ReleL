package ht.berth.relel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecyclerCustomAdapter extends RecyclerView.Adapter<RecyclerCustomAdapter.MyHolder> implements Filterable {
    private Context context;
    private ArrayList<String>  contactId, fnameAL, lnameAL, numberAL, addressAL , emailAL;
    Activity activity;
    Animation animation;
    FloatingActionButton edit;
    public RecyclerCustomAdapter(Activity activity, Context context,ArrayList<String> id, ArrayList<String> fnameAL, ArrayList<String> lnameAL, ArrayList<String> numberAL, ArrayList<String> addressAL, ArrayList<String> emailAL) {
        this.activity = activity;
        this.context = context;
        this.contactId = id;
        this.fnameAL = fnameAL;
        this.lnameAL = lnameAL;
        this.numberAL = numberAL;
        this.addressAL = addressAL;
        this.emailAL = emailAL;
    }

    public static void sendIntent() {
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custome_recycler,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.IdTV.setText(String.valueOf(contactId.get(position)));
        holder.firstName.setText(String.valueOf(fnameAL.get(position)));
        holder.numberTV.setText(String.valueOf(numberAL.get(position)));
        holder.lastName.setText(String.valueOf(lnameAL.get(position)));
        // Ou add id an pouw ka update men ou set li false
        holder.IdTV.setVisibility(View.GONE);
        holder.image.setImageResource(R.drawable.ic_person);
        holder.cusRecLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Item.class);
                intent.putExtra("Id", String.valueOf(contactId.get(position)));
                intent.putExtra("FirstName", String.valueOf(fnameAL.get(position)));
                intent.putExtra("LastName", String.valueOf(lnameAL.get(position)));
                intent.putExtra("Number", String.valueOf(numberAL.get(position)));
                intent.putExtra("Address", String.valueOf(addressAL.get(position)));
                intent.putExtra("Email", String.valueOf(emailAL.get(position)));
                activity.startActivityForResult(intent, 1);
            }

        });
    }

    @Override
    public int getItemCount() {
        //Ou paka mete saw pa sevi ak yo an
        return contactId.size();
    }

    @Override
    public Filter getFilter() {

        return null;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName, numberTV , IdTV ;
        LinearLayout cusRecLay;
        ImageView image;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            IdTV = itemView.findViewById(R.id.ID);
            firstName = itemView.findViewById(R.id.name_in_recycler);
            numberTV = itemView.findViewById(R.id.number_in_recycler);
            cusRecLay =itemView.findViewById(R.id.custom_rec_lin);
            animation = AnimationUtils.loadAnimation(context, R.anim.my_animation);
            lastName = itemView.findViewById(R.id.last_name);
            image = itemView.findViewById(R.id.image);
            cusRecLay.setAnimation(animation);
        }
    }
}
