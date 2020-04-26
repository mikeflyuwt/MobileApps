package edu.uw.main.ui.blog;

import android.graphics.drawable.Icon;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.main.R;
import edu.uw.main.databinding.FragmentBlogCardBinding;


public class BlogRecyclerViewAdapter extends
        RecyclerView.Adapter<BlogRecyclerViewAdapter.BlogViewHolder>  {
    //Store all of the blogs to present
    private final List<BlogPost> mBlogs;
    public BlogRecyclerViewAdapter(List<BlogPost> items) {
        this.mBlogs = items;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BlogViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_blog_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        holder.setBlog(mBlogs.get(position));
    }


    @Override
    public int getItemCount() {
        return mBlogs.size();
    }

    /**
     * Objects from this class represent an Individual row View from the List
     * of rows in the Blog Recycler View.
     */
    public class BlogViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentBlogCardBinding binding;
        public BlogViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentBlogCardBinding.bind(view);
            binding.buittonMore.setOnClickListener(this::handleMoreOrLess);
        }
        /**
         * When the button is clicked in the more state, expand the card to display
         * the blog preview and switch the icon to the less state. When the button
         * is clicked in the less state, shrink the card and switch the icon to the
         * more state.
         * @param button the button that was clicked
         */
        private void handleMoreOrLess(final View button) {
            if (binding.textPreview.getVisibility() == View.GONE) {
                binding.textPreview.setVisibility(View.VISIBLE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_less_grey_24dp));
            } else {
                binding.textPreview.setVisibility(View.GONE);
                binding.buittonMore.setImageIcon(
                        Icon.createWithResource(
                                mView.getContext(),
                                R.drawable.ic_more_grey_24dp));
            }
        }
        void setBlog(final BlogPost blog) {
            binding.buttonFullPost.setOnClickListener(view ->
                    Navigation.findNavController(mView).navigate(
                            BlogListFragmentDirections
                                    .actionNavigationBlogsToBlogPostFragment(blog))
            );
            binding.textTitle.setText(blog.getTitle());
            binding.textPubdate.setText(blog.getPubDate());
            //Use methods in the HTML class to format the HTML found in the text
            final String preview = Html.fromHtml(
                    blog.getTeaser(),
                    Html.FROM_HTML_MODE_COMPACT)
                    .toString().substring(0,100) //just a preview of the teaser
                    + "...";
            binding.textPreview.setText(preview);
        }
    }
}
