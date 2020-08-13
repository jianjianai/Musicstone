package cn.jji8.Musicstone;

import com.xxmicloxx.NoteBlockAPI.model.CustomInstrument;
import com.xxmicloxx.NoteBlockAPI.model.Layer;
import com.xxmicloxx.NoteBlockAPI.model.Note;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class io {
    public static void jiazai(CommandSender q, String 文件名字){
        File File = new File(main.main.getDataFolder(),文件名字+".nbs");
        if(!File.exists()){
            q.sendMessage("没有找到文件："+文件名字+".nbs");
            return;
        }
        if(!(q instanceof Player)){
            q.sendMessage("必须要玩家执行");
            return;
        }
        Player Player = (org.bukkit.entity.Player) q;
        World world = Player.getLocation().getWorld();

        Song song = NBSDecoder.parse(File);
        Map<Integer, Layer> 层 = song.getLayerHashMap();
        int 歌的高度 = song.getSongHeight();
        int 长度 = song.getLength();
        String 标题 = song.getTitle();
        String 作者 = song.getAuthor();
        float 速率 = song.getSpeed();
        float 延迟 = song.getDelay();
        CustomInstrument[] 虚拟仪表 = song.getCustomInstruments();
        int 第一款定制仪器索引 = song.getFirstCustomInstrumentIndex();
        boolean 立体声 = song.isStereo();
        int a =0 ;
        if(速率<=10){ a = 1; }
        if(速率<=7.5){ a = 2; }
        if(速率<=5){ a = 3; }
        if(速率<=2.5){ a = 4; }

        int r = 0;
        ArrayList<Layer> biao = new ArrayList();
        for(int i = 0;r<层.size();i++) {
            Layer Layer = 层.get(new Integer(i));
            if(Layer!=null){
                biao.add(Layer);
                r++;
            }
        }


        for(int ii = 0;ii<长度;ii++) {
            System.out.println("第"+ii+"行");
            for (int i = 0; i < biao.size(); i++) {
                Note Note = biao.get(i).getNotesAtTicks().get(new Integer(ii));//取出每一行
                Block Block = new Location(world, i * 2 + 0, 4, ii * 2 + 0).getBlock();
                Block Block2 = new Location(world, i * 2 + 0, 4, ii * 2 - 1 + 0).getBlock();

                Block Block4 = new Location(world, i * 2 + 0, 5, ii * 2 + 0).getBlock();
                Block Block3 = new Location(world, i * 2 + 0, 5, ii * 2 - 1 + 0).getBlock();

                Block2.setType(Material.GLASS);
                Block3.setType(Material.REPEATER);
                Repeater Repeater = (Repeater) Block3.getBlockData();//中继器
                Repeater.setDelay(a);
                Block3.setBlockData(Repeater);

                if (Note == null) {
                    Block.setType(Material.GLASS);//设置音符合下面的方块
                    Block4.setType(Material.REDSTONE_WIRE);
                    //System.out.println("空");
                } else {
                    Block.setType(yueqi(Note.getInstrument()));//设置音符合下面的方块
                    Block4.setType(Material.NOTE_BLOCK);
                    NoteBlock NoteBlock = (NoteBlock) Block4.getBlockData();
                    int T = Note.getKey() - 33;
                    if (T < 0) {
                        NoteBlock.setNote(new org.bukkit.Note(0));
                    } else if (T > 24) {
                        NoteBlock.setNote(new org.bukkit.Note(24));
                    } else {
                        NoteBlock.setNote(new org.bukkit.Note(T));
                    }
                    NoteBlock.setInstrument(yfh(Note.getInstrument()));
                    Block4.setBlockData(NoteBlock);
                }
            }
        }

/*
        int r = 0;
        for(int i = 0;r<层.size();i++){
            Layer Layer = 层.get(new Integer(i));
            if(!(Layer==null)){
                r++;
                HashMap<Integer, Note> map = Layer.getNotesAtTicks();
                System.out.println("层___"+i);
                for(int ii = 0;ii<map.size();ii++){
                    Note Note = map.get(new Integer(ii));
                    Block Block = new Location(world,i*2+0,4,ii*2+0).getBlock();
                    Block Block2 = new Location(world,i*2+0,4,ii*2-1+0).getBlock();

                    Block Block4 = new Location(world,i*2+0,5,ii*2+0).getBlock();
                    Block Block3 = new Location(world,i*2+0,5,ii*2-1+0).getBlock();

                    Block2.setType(Material.GLASS);
                    Block3.setType(Material.REPEATER);
                    Repeater Repeater = (Repeater) Block3.getBlockData();//中继器
                    Repeater.setDelay(a);
                    Block3.setBlockData(Repeater);

                    if(Note==null){
                        Block.setType(Material.GLASS);//设置音符合下面的方块
                        Block4.setType(Material.REDSTONE_WIRE);
                        //System.out.println("空");
                    }else {
                        Block.setType(yueqi(Note.getInstrument()));//设置音符合下面的方块
                        Block4.setType(Material.NOTE_BLOCK);
                        NoteBlock NoteBlock = (NoteBlock) Block4.getBlockData();
                        int T = Note.getKey()-33;
                        if(T<0){
                            NoteBlock.setNote(new org.bukkit.Note(0));
                        }else if(T>24){
                            NoteBlock.setNote(new org.bukkit.Note(24));
                        }else {
                            NoteBlock.setNote(new org.bukkit.Note(T));
                        }
                        NoteBlock.setInstrument(yfh(Note.getInstrument()));
                        Block4.setBlockData(NoteBlock);
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("层");
                        System.out.println("乐器:"+Note.getInstrument()+" 速度："+Note.getVelocity()+" key："+Note.getKey()+" 拍："+Note.getPanning()+" 调："+Note.getPitch());
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("歌的高度");
                        System.out.println(歌的高度);
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("长度");
                        System.out.println(长度);
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("标题");
                        System.out.println(标题);
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("作者");
                        System.out.println(作者);
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("速率");
                        System.out.println(速率);
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("延迟");
                        System.out.println(延迟);
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("虚拟仪表");
                        System.out.println(虚拟仪表);
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("第一款定制仪器索引");
                        System.out.println(第一款定制仪器索引);
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("立体声");
                        System.out.println(立体声);
                        System.out.println("-----------------------------------------------------------");
                    }
                }
            }
        }
        System.out.println(层);
 */
    }
    static Material yueqi(int a){
        switch (a){
            case 0:return Material.DIRT;
            case 1:return Material.OAK_PLANKS;
            case 2:return Material.COBBLESTONE;
            case 3:return Material.SAND;
            case 4:return Material.GLASS;
            case 5:return Material.WHITE_WOOL;
            case 6:return Material.CLAY;
            case 7:return Material.GOLD_BLOCK;
            case 8:return Material.PACKED_ICE;
            case 9:return Material.BONE_BLOCK;
            default:return Material.DIRT;
        }
    }
    static Instrument yfh(int a){
        switch (a){
            case 0:return Instrument.PIANO;
            case 1:return Instrument.BASS_GUITAR;
            case 2:return Instrument.BASS_DRUM;
            case 3:return Instrument.SNARE_DRUM;
            case 4:return Instrument.STICKS;
            case 5:return Instrument.GUITAR;
            case 6:return Instrument.FLUTE;
            case 7:return Instrument.BELL;
            case 8:return Instrument.CHIME;
            case 9:return Instrument.XYLOPHONE;
            default:return Instrument.PIANO;
        }
    }
}
