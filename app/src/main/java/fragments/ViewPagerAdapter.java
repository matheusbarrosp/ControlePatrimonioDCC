package fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

   public static int SEARCH_FRAMGMENT = 0;
   private CharSequence titles[];
   private SearchFragment searchFragment;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        titles = new CharSequence[2];
        titles[0] = "Consulta";
        titles[1] = "Cadastro";
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        if(position == SEARCH_FRAMGMENT){
            if(searchFragment == null){
                searchFragment = new SearchFragment();
            }
            return searchFragment;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            RegisterFragment tab2 = new RegisterFragment();
            return tab2;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return titles.length;
    }
}
