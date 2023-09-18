package kr.co.company.it_maverick.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import kr.co.company.it_maverick.Club.DiaryFragment;
import kr.co.company.it_maverick.Fragment.ClubFragment;
import kr.co.company.it_maverick.Fragment.FeedFragment;
import kr.co.company.it_maverick.Club.ProFragment;

public class TabPagerAdapter extends FragmentStateAdapter {

    Fragment[] fragments = new Fragment[] {new FeedFragment(), new DiaryFragment(), new ProFragment()};

    public TabPagerAdapter(@NonNull ClubFragment fragmentActivity) {
        super (fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }
}
