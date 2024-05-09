package com.example.musicplayer;

import java.util.ArrayList;

public class Music {

    public int number;
    public int rawId;
    public int resId;
    public String name;
    public String type;
    public String singer;

    public Music(int number,int rawId,int resId,String name,String type,String singer){
        this.number=number;
        this.rawId=rawId;
        this.resId=resId;
        this.name=name;
        this.type=type;
        this.singer=singer;
    }

    public static ArrayList<ArrayList<Music>> initMM(){
        ArrayList<ArrayList<Music>> mM=new ArrayList<>();
        ArrayList<Music> musics = new ArrayList<>();
        musics.add(new Music(1,R.raw.slq,R.mipmap.slq,"舍离去","Pop","王子健"));
        musics.add(new Music(2,R.raw.girl,R.mipmap.girl,"girl","Pop","Alexander 23"));
        musics.add(new Music(3,R.raw.his_theme,R.mipmap.his_theme,"His Theme","New Age","Toby Fox"));
        musics.add(new Music(4,R.raw.sunroof,R.mipmap.sunroof,"Sunroof","Pop","Nicky Youre/dazy"));
        musics.add(new Music(5,R.raw.bad_guy,R.mipmap.bad_guy,"bad guy","Electropop","Billie Eilish"));
        musics.add(new Music(6,R.raw.booty_music,R.mipmap.booty_music,"Booty Music","Pop","Deep Side"));
        musics.add(new Music(7,R.raw.head_in_the_clouds,R.mipmap.head_in_the_clouds,"Head In The Clouds","Pop","Hayd"));
        musics.add(new Music(8,R.raw.fool_for_you,R.mipmap.fool_for_you,"Fool For You","House","Kastra"));
        musics.add(new Music(9,R.raw.dancing_with_a_stranger,R.mipmap.dancing_with_a_stranger,"Dancing With A Stranger","Pop","Sam Smith/Normani"));
        musics.add(new Music(10,R.raw.gnypmfys,R.mipmap.gnypmfys,"给你一瓶魔法药水","Pop Rock","告五人"));
        mM.add(musics);
        ArrayList<Music> MVs = new ArrayList<>();
        MVs.add(new Music(1,R.raw.dshh,R.mipmap.dshh,"打上花火","Pop","DAOKO/米津玄师"));
        MVs.add(new Music(2,R.raw.hypnodancer,R.mipmap.hypnodancer,"Hypnodancer","Pop","Little Big"));
        MVs.add(new Music(3,R.raw.loser,R.mipmap.loser,"Loser","New Age","米津玄师"));
        MVs.add(new Music(4,R.raw.mz,R.mipmap.mz,"芒种","Pop","音阙诗听/赵方婧"));
        MVs.add(new Music(5,R.raw.teeth,R.mipmap.teeth,"Teeth","Electropop","5 Second Of Summer"));
        MVs.add(new Music(6,R.raw.uno,R.mipmap.uno,"Uno","Pop","Little Big"));
        MVs.add(new Music(7,R.raw.wake_up,R.mipmap.wake_up,"Wake Up","Pop","Elaine"));
        MVs.add(new Music(8,R.raw.walk_thru_fire,R.mipmap.walk_thru_fire,"Walk Thru Fire","House","Vicetone/Meron Ryan"));
        MVs.add(new Music(9,R.raw.yellow,R.mipmap.yellow,"Yellow","Pop","神山羊"));
        MVs.add(new Music(10,R.raw.ysgs,R.mipmap.ysgs,"雅俗共赏","Pop Rock","许嵩"));
        mM.add(MVs);
        return mM;
    }

}
