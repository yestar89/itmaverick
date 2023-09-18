package kr.co.company.it_maverick;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager.widget.PagerAdapter;
import kr.co.company.it_maverick.Fragment.ClubFragment;

public class MyViewPageAdapter extends FragmentStateAdapter {
    public MyViewPageAdapter(@NonNull ClubFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Feed(); // 첫 번째 탭의 프래그먼트
            case 1:
                return new DiaryFragment(); // 두 번째 탭의 프래그먼트
            case 2:
                return new ProFragment(); // 세 번째 탭의 프래그먼트
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
