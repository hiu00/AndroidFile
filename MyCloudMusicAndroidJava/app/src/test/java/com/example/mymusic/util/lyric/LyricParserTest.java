package com.example.mymusic.util.lyric;

import static com.example.mymusic.util.Constant.LRC;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.mymusic.domain.Lyric.Lyric;

import org.junit.Test;

/**
 * 解析歌词工具类测试
 */
public class LyricParserTest {
    /**
     * LRC歌词
     * 其中的\n不要删除
     * 是每一行歌词的分隔符
     */
    private String lrcLyric="[ti:爱的代价]\n[ar:李宗盛]\n[al:爱的代价]\n[00:00.50]爱的代价\n[00:02.50]演唱：李宗盛\n[00:06.50]\n[00:16.70]还记得年少时的梦吗\n[00:21.43]像朵永远不调零的花\n[00:25.23]陪我经过那风吹雨打\n[00:28.59]看世事无常\n[00:30.57]看沧桑变化\n[00:33.31]那些为爱所付出的代价\n[00:37.10]是永远都难忘的啊\n[00:41.10]所有真心的痴心的话\n[00:44.57]永在我心中虽然已没有他\n[00:51.46]走吧 走吧 人总要学着自己长大\n[00:59.53]走吧 走吧 人生难免经历苦痛挣扎\n[01:07.19]走吧 走吧 为自己的心找一个家\n[01:15.41]也曾伤心流泪\n[01:17.55]也曾黯然心碎\n[01:19.57]这是爱的代价\n[01:22.57]\n[01:40.67]也许我偶尔还是会想他\n[01:45.28]偶尔难免会惦记着他\n[01:49.10]就当他是个老朋友吧\n[01:52.60]也让我心疼也让我牵挂\n[01:57.37]只是我心中不再有火花\n[02:01.21]让往事都随风去吧\n[02:05.10]所有真心的痴心的话\n[02:08.53]仍在我心中\n[02:10.39]虽然已没有他\n[02:15.26]走吧 走吧 人总要学着自己长大\n[02:23.14]走吧 走吧 人生难免经历苦痛挣扎\n[02:31.26]走吧 走吧 为自己的心找一个家\n[02:39.22]也曾伤心流泪\n[02:41.54]也曾黯然心碎\n[02:43.60]这是爱的代价\n[02:46.44]\n[03:22.77]走吧 走吧 人总要学着自己长大\n[03:31.16]走吧 走吧 人生难免经历苦痛挣扎\n[03:39.08]走吧 走吧 为自己的心找一个家\n[03:47.12]也曾伤心流泪\n[03:49.41]也曾黯然心碎\n[03:51.58]这是爱的代价\n";

    /**
     * KSC歌词
     */
    private String kscLyric = "karaoke := CreateKaraokeObject;\nkaraoke.rows := 2;\nkaraoke.TimeAfterAnimate := 2000;\nkaraoke.TimeBeforeAnimate := 4000;\nkaraoke.clear;\n\nkaraoke.add('00:27.487', '00:32.068', '一时失志不免怨叹', '347,373,1077,320,344,386,638,1096');\nkaraoke.add('00:33.221', '00:38.068', '一时落魄不免胆寒', '282,362,1118,296,317,395,718,1359');\nkaraoke.add('00:38.914', '00:42.164', '那通失去希望', '290,373,348,403,689,1147');\nkaraoke.add('00:42.485', '00:44.530', '每日醉茫茫', '298,346,366,352,683');\nkaraoke.add('00:45.273', '00:49.029', '无魂有体亲像稻草人', '317,364,380,351,326,351,356,389,922');\nkaraoke.add('00:50.281', '00:55.585', '人生可比是海上的波浪', '628,1081,376,326,406,371,375,1045,378,318');\nkaraoke.add('00:56.007', '01:00.934', '有时起有时落', '303,362,1416,658,750,1438');\nkaraoke.add('01:02.020', '01:04.581', '好运歹运', '360,1081,360,760');\nkaraoke.add('01:05.283', '01:09.453', '总嘛要照起来行', '303,338,354,373,710,706,1386');\nkaraoke.add('01:10.979', '01:13.029', '三分天注定', '304,365,353,338,690');\nkaraoke.add('01:13.790', '01:15.950', '七分靠打拼', '356,337,338,421,708');\nkaraoke.add('01:16.339', '01:20.870', '爱拼才会赢', '325,1407,709,660,1430');\nkaraoke.add('01:33.068', '01:37.580', '一时失志不免怨叹', '307,384,1021,363,357,374,677,1029');\nkaraoke.add('01:38.660', '01:43.656', '一时落魄不免胆寒', '381,411,1067,344,375,381,648,1389');\nkaraoke.add('01:44.473', '01:47.471', '那通失去希望', '315,365,340,369,684,925');\nkaraoke.add('01:48.000', '01:50.128', '每日醉茫茫', '338,361,370,370,689');\nkaraoke.add('01:50.862', '01:54.593', '无魂有体亲像稻草人', '330,359,368,376,325,334,352,389,898');\nkaraoke.add('01:55.830', '02:01.185', '人生可比是海上的波浪', '654,1056,416,318,385,416,373,1032,342,363');\nkaraoke.add('02:01.604', '02:06.716', '有时起有时落', '303,330,1432,649,704,1694');\nkaraoke.add('02:07.624', '02:10.165', '好运歹运', '329,1090,369,753');\nkaraoke.add('02:10.829', '02:15.121', '总嘛要照起来行', '313,355,362,389,705,683,1485');\nkaraoke.add('02:16.609', '02:18.621', '三分天注定', '296,363,306,389,658');\nkaraoke.add('02:19.426', '02:21.428', '七分靠打拼', '330,359,336,389,588');\nkaraoke.add('02:21.957', '02:26.457', '爱拼才会赢', '315,1364,664,767,1390');\nkaraoke.add('02:50.072', '02:55.341', '人生可比是海上的波浪', '656,1086,349,326,359,356,364,1095,338,340');\nkaraoke.add('02:55.774', '03:01.248', '有时起有时落', '312,357,1400,670,729,2006');\nkaraoke.add('03:01.787', '03:04.369', '好运歹运', '341,1084,376,781');\nkaraoke.add('03:05.041', '03:09.865', '总嘛要起工来行', '305,332,331,406,751,615,2084');\nkaraoke.add('03:10.754', '03:12.813', '三分天注定', '309,359,361,366,664');\nkaraoke.add('03:13.571', '03:15.596', '七分靠打拼', '320,362,349,352,642');\nkaraoke.add('03:16.106', '03:20.688', '爱拼才会赢', '304,1421,661,706,1490');";
    /**
     * 测试LRC歌词歌词
     */
    @Test
    public void testLRCParse(){
        //解析歌词
        Lyric lyric = LyricParser.parse(LRC, lrcLyric);

        //确认返回的数组大于0
        //因为我们给的数据是正确的
        //所以结果肯定大于0才正确
        assertTrue(lyric.getDatum().size()>0);

        //不太好判断歌词是否解析正确
        //所以就判断第10行歌词的开始时间必须大于0
        //第10行歌词必须有内容
        //因为我们提供的歌词是正确的
        assertTrue(lyric.getDatum().get(10).getStartTime()>0);

        //歌词内容也不为空
        assertNotNull(lyric.getDatum().get(10).getData());
    }
}