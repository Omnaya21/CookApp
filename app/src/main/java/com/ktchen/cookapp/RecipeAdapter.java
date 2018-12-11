package com.ktchen.cookapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * The adapter that allows the recyclerView to show recipes.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private List<Recipe> recipes = new ArrayList<Recipe>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.mInflater = LayoutInflater.from(context);
        this.recipes = recipes;
    }

    /**
     * OnCreate for the adapter.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    @NonNull
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        RecipeAdapter.ViewHolder recipesViewHolder = new RecipeAdapter.ViewHolder(view, context);
        return recipesViewHolder;
        //return new ViewHolder(view);
    }

    /**
     * On BindViewHolder for the adapter.
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder viewHolder, int position) {
        Recipe recipe = recipes.get(position);

        TextView textView = viewHolder.textView;
        textView.setText(recipe.getTitle());
    }

    /**
     * Gets the item count.
     *
     * @return an int item.
     */
    @Override
    public int getItemCount() {
        if (recipes != null)
            return recipes.size();
        else return 0;
    }

    /**
     * ViewHolder for the RecyclerView adapter.
     */
    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        View view;
        Context context;
        public TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            view = itemView;
            context = ctx;
            textView = itemView.findViewById(R.id.textView);
        }

        /**
         * Click listener for the adapter.  Gets the position of where you clicked.
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Toast.makeText(context, "Clicked on context menu", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Actions");
            menu.add(0, R.id.edit, getAdapterPosition(), "edit");
            menu.add(0, R.id.delete, getAdapterPosition(), "Delete");
        }
    }

    /**
     * Convenience method for getting data at click position.
     *
     * @param id
     * @return Recipie
     */
    Recipe getItem(int id) {
        return recipes.get(id);
    }

    /**
     * allows clicks events to be caught
     *
     * @param itemClickListener
     */
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * parent activity will implement this method to respond to click events.
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}

