package com.ktchen.cookapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
private List<Recipe> recipes= new ArrayList<Recipe>();
public RecipeAdapter(List<Recipe> recipes){
    this.recipes= recipes;
}

public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public ViewHolder(View itemView){
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.textView);
        }
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context= parent.getContext();
        LayoutInflater inflater= LayoutInflater.from(context);

        View recipeView= inflater.inflate(R.layout.recyclerview_item,parent, false);
       ViewHolder viewHolder = new ViewHolder(recipeView);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder viewHolder,int position) {
        Recipe recipe = recipes.get(position);

        TextView textView= viewHolder.textView;
        textView.setText(recipe.getTitle());
    }


    @Override
    public int getItemCount() {
    if(recipes!=null)
       return recipes.size();
    else return 0;
    }

}

