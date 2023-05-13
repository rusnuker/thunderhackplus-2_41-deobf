//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.util.concurrent.atomic.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import com.mrzak34.thunderhack.modules.movement.*;
import com.mrzak34.thunderhack.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.util.math.*;
import org.lwjgl.input.*;
import java.util.concurrent.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.material.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import net.minecraft.util.text.*;
import com.mrzak34.thunderhack.command.*;
import java.util.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.modules.render.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.phobos.*;
import net.minecraftforge.common.*;

public class AutoCrystal extends Module
{
    public static final PositionHistoryHelper POSITION_HISTORY;
    private static final ScheduledExecutorService EXECUTOR;
    private static final AtomicBoolean ATOMIC_STARTED;
    public static Timer timercheckerfg;
    public static Timer timercheckerwfg;
    public static boolean psdead;
    private static boolean started;
    public final Map<BlockPos, CrystalTimeStamp> placed;
    public final HelperSequential sequentialHelper;
    public final IDHelper idHelper;
    public final HelperRotation rotationHelper;
    public final HelperRange rangeHelper;
    private final MouseFilter pitchMouseFilter;
    private final MouseFilter yawMouseFilter;
    private final Map<BlockPos, Long> fadeList;
    public Setting<settingtypeEn> settingType;
    public Setting<pages> page;
    public final Setting<Integer> extrapol;
    public final Setting<Integer> bExtrapol;
    public final Setting<Integer> blockExtrapol;
    public final Setting<BlockExtrapolationMode> blockExtraMode;
    public final Setting<Boolean> doubleExtraCheck;
    public final Setting<Boolean> avgPlaceDamage;
    public final Setting<Double> placeExtraWeight;
    public final Setting<Double> placeNormalWeight;
    public final Setting<Boolean> avgBreakExtra;
    public final Setting<Double> breakExtraWeight;
    public final Setting<Double> breakNormalWeight;
    public final Setting<Boolean> gravityExtrapolation;
    public final Setting<Double> gravityFactor;
    public final Setting<Double> yPlusFactor;
    public final Setting<Double> yMinusFactor;
    public final Setting<Boolean> selfExtrapolation;
    public final Setting<Boolean> useSafetyFactor;
    public final Setting<Double> selfFactor;
    public final Setting<Double> safetyFactor;
    public final Setting<Double> compareDiff;
    public final Setting<Boolean> facePlaceCompare;
    public Setting<Boolean> place;
    public Setting<Target> targetMode;
    public Setting<Float> placeRange;
    public Setting<Float> placeTrace;
    public Setting<Float> minDamage;
    public Setting<Integer> placeDelay;
    public DiscreteTimer placeTimer;
    public Setting<Float> maxSelfPlace;
    public Setting<Integer> multiPlace;
    public Setting<Float> slowPlaceDmg;
    public Setting<Integer> slowPlaceDelay;
    public Setting<Boolean> override;
    public Setting<Boolean> newVer;
    public Setting<Boolean> newVerEntities;
    public Setting<SwingTime> placeSwing;
    public Setting<Boolean> smartTrace;
    public Setting<Boolean> placeRangeEyes;
    public Setting<Boolean> placeRangeCenter;
    public Setting<Double> traceWidth;
    public Setting<Boolean> fallbackTrace;
    public Setting<Boolean> rayTraceBypass;
    public Setting<Boolean> forceBypass;
    public Setting<Boolean> rayBypassFacePlace;
    public Setting<Boolean> rayBypassFallback;
    public Setting<Integer> bypassTicks;
    public Setting<Float> rbYaw;
    public Setting<Float> rbPitch;
    public Setting<Integer> bypassRotationTime;
    public Setting<Boolean> ignoreNonFull;
    public Setting<Boolean> efficientPlacements;
    public Setting<Integer> simulatePlace;
    public FakeCrystalRender crystalRender;
    public Setting<Attack2> attackMode;
    public Setting<Boolean> attack;
    public Setting<Float> breakRange;
    public Setting<Integer> breakDelay;
    public DiscreteTimer breakTimer;
    public Setting<Float> breakTrace;
    public Setting<Float> minBreakDamage;
    public Setting<Float> maxSelfBreak;
    public Setting<Float> slowBreakDamage;
    public Setting<Integer> slowBreakDelay;
    public Setting<Boolean> instant;
    public Setting<Boolean> asyncCalc;
    public Setting<Boolean> alwaysCalc;
    public Setting<Boolean> ncpRange;
    public Setting<SmartRange> placeBreakRange;
    public Setting<Integer> smartTicks;
    public Setting<Integer> negativeTicks;
    public Setting<Boolean> smartBreakTrace;
    public Setting<Boolean> negativeBreakTrace;
    public Setting<Integer> packets;
    public Setting<Boolean> overrideBreak;
    public Setting<AntiWeakness> antiWeakness;
    public Setting<Boolean> instantAntiWeak;
    public Setting<Boolean> efficient;
    public Setting<Boolean> manually;
    public Setting<Integer> manualDelay;
    public Setting<SwingTime> breakSwing;
    public Setting<ACRotate> rotate;
    public Setting<RotateMode> rotateMode;
    public Setting<Float> smoothSpeed;
    public Setting<Integer> endRotations;
    public Setting<Float> angle;
    public Setting<Float> placeAngle;
    public Setting<Float> height;
    public Setting<Double> placeHeight;
    public Setting<Integer> rotationTicks;
    public Setting<Boolean> focusRotations;
    public Setting<Boolean> focusAngleCalc;
    public Setting<Double> focusExponent;
    public Setting<Double> focusDiff;
    public Setting<Double> rotationExponent;
    public Setting<Double> minRotDiff;
    public Setting<Integer> existed;
    public Setting<Boolean> pingExisted;
    public Setting<Float> targetRange;
    public Setting<Float> pbTrace;
    public Setting<Float> range;
    public Setting<Boolean> suicide;
    public Setting<Boolean> shield;
    public Setting<Integer> shieldCount;
    public Setting<Float> shieldMinDamage;
    public Setting<Float> shieldSelfDamage;
    public Setting<Integer> shieldDelay;
    public Setting<Float> shieldRange;
    public Setting<Boolean> shieldPrioritizeHealth;
    public Setting<Boolean> multiTask;
    public Setting<Boolean> multiPlaceCalc;
    public Setting<Boolean> multiPlaceMinDmg;
    public Setting<Boolean> countDeadCrystals;
    public Setting<Boolean> countDeathTime;
    public Setting<Boolean> yCalc;
    public Setting<Boolean> dangerSpeed;
    public Setting<Float> dangerHealth;
    public Setting<Integer> cooldown;
    public WeaknessHelper weaknessHelper;
    public Setting<Integer> placeCoolDown;
    public Setting<AntiFriendPop> antiFriendPop;
    public Setting<Boolean> antiFeetPlace;
    public Setting<Integer> feetBuffer;
    public ServerTimeHelper serverTimeHelper;
    public Setting<Boolean> stopWhenEating;
    public Setting<Boolean> stopWhenMining;
    public Setting<Boolean> dangerFacePlace;
    public Setting<Boolean> motionCalc;
    public Setting<Boolean> holdFacePlace;
    public Setting<Float> facePlace;
    public Setting<Float> minFaceDmg;
    public Setting<Float> armorPlace;
    public Setting<Boolean> pickAxeHold;
    public Setting<Boolean> antiNaked;
    public Setting<Boolean> fallBack;
    public Setting<Float> fallBackDiff;
    public Setting<Float> fallBackDmg;
    public Setting<AutoSwitch> autoSwitch;
    public Setting<Boolean> mainHand;
    public Setting<SubBind> switchBind;
    public Setting<Boolean> switchBack;
    public Setting<Boolean> useAsOffhand;
    public Setting<Boolean> instantOffhand;
    public Setting<Boolean> switchMessage;
    public Setting<SwingType> swing;
    public Setting<SwingType> placeHand;
    public Setting<CooldownBypass2> cooldownBypass;
    public Setting<CooldownBypass2> obsidianBypass;
    public Setting<CooldownBypass2> antiWeaknessBypass;
    public Setting<CooldownBypass2> mineBypass;
    public Setting<SwingType> obbyHand;
    public Setting<Boolean> render;
    public Setting<Integer> renderTime;
    public Setting<Boolean> box;
    public Setting<Boolean> fade;
    public Setting<Boolean> fadeComp;
    public Setting<Integer> fadeTime;
    public Setting<Boolean> realtime;
    public Setting<Boolean> slide;
    public Setting<Boolean> smoothSlide;
    public Setting<Integer> slideTime;
    public Setting<Boolean> zoom;
    public Setting<Double> zoomTime;
    public Setting<Double> zoomOffset;
    public Setting<Boolean> multiZoom;
    public Setting<Boolean> renderExtrapolation;
    public Setting<RenderDamagePos> renderDamage;
    public Setting<RenderDamage> renderMode;
    public Setting<Boolean> setDead;
    public Setting<Boolean> instantSetDead;
    public Setting<Boolean> pseudoSetDead;
    public Setting<Boolean> simulateExplosion;
    public Setting<Boolean> soundRemove;
    public Setting<Boolean> useSafeDeathTime;
    public Setting<Integer> safeDeathTime;
    public Setting<Integer> deathTime;
    public Setting<Boolean> obsidian;
    public Setting<Boolean> basePlaceOnly;
    public Setting<Boolean> obbySwitch;
    public Setting<Integer> obbyDelay;
    public Setting<Integer> obbyCalc;
    public Setting<Integer> helpingBlocks;
    public Setting<Float> obbyMinDmg;
    public Setting<Boolean> terrainCalc;
    public Setting<Boolean> obbySafety;
    public Setting<RayTraceMode> obbyTrace;
    public Setting<Boolean> obbyTerrain;
    public Setting<Boolean> obbyPreSelf;
    public Setting<Integer> fastObby;
    public Setting<Integer> maxDiff;
    public Setting<Double> maxDmgDiff;
    public Setting<Boolean> setState;
    public Setting<PlaceSwing> obbySwing;
    public Setting<Boolean> obbyFallback;
    public Setting<Rotate> obbyRotate;
    public Setting<Boolean> interact;
    public Setting<Boolean> inside;
    public Setting<Boolean> lava;
    public Setting<Boolean> water;
    public Setting<Boolean> liquidObby;
    public Setting<Boolean> liquidRayTrace;
    public Setting<Integer> liqDelay;
    public Setting<Rotate> liqRotate;
    public Setting<Boolean> pickaxeOnly;
    public Setting<Boolean> interruptSpeedmine;
    public Setting<Boolean> setAir;
    public Setting<Boolean> absorb;
    public Setting<Boolean> requireOnGround;
    public Setting<Boolean> ignoreLavaItems;
    public Setting<Boolean> sponges;
    public Setting<Boolean> antiTotem;
    public Setting<Float> totemHealth;
    public AntiTotemHelper antiTotemHelper;
    public Setting<Float> minTotemOffset;
    public Setting<Float> maxTotemOffset;
    public Setting<Float> popDamage;
    public Setting<Boolean> totemSync;
    public Setting<Boolean> forceAntiTotem;
    public Setting<Boolean> forceSlow;
    public Setting<Boolean> syncForce;
    public Setting<Boolean> dangerForce;
    public Setting<Integer> forcePlaceConfirm;
    public Setting<Integer> forceBreakConfirm;
    public Setting<Integer> attempts;
    public Setting<Boolean> damageSync;
    public Setting<Boolean> preSynCheck;
    public Setting<Boolean> discreteSync;
    public Setting<Boolean> dangerSync;
    public Setting<Integer> placeConfirm;
    public Setting<Integer> breakConfirm;
    public Setting<Integer> syncDelay;
    public DamageSyncHelper damageSyncHelper;
    public ForceAntiTotemHelper forceHelper;
    public Setting<Boolean> surroundSync;
    public Setting<Boolean> idPredict;
    public Setting<Integer> idOffset;
    public Setting<Integer> idDelay;
    public Setting<Integer> idPackets;
    public Setting<Boolean> godAntiTotem;
    public Setting<Boolean> holdingCheck;
    public Setting<Boolean> toolCheck;
    public Setting<PlaceSwing> godSwing;
    public Setting<PreCalc> preCalc;
    public Setting<ExtrapolationType> preCalcExtra;
    public Setting<Float> preCalcDamage;
    public Setting<Boolean> multiThread;
    public Setting<Boolean> smartPost;
    public Setting<Boolean> mainThreadThreads;
    public Setting<RotationThread> rotationThread;
    public Setting<Float> partial;
    public Setting<Integer> maxCancel;
    public RotationCanceller rotationCanceller;
    public Setting<Integer> timeOut;
    public Setting<Boolean> blockDestroyThread;
    public Setting<Integer> threadDelay;
    public ThreadHelper threadHelper;
    public Setting<Integer> tickThreshold;
    public Setting<Integer> preSpawn;
    public Setting<Integer> maxEarlyThread;
    public Setting<Integer> pullBasedDelay;
    public Setting<Boolean> explosionThread;
    public Setting<Boolean> soundThread;
    public Setting<Boolean> entityThread;
    public Setting<Boolean> spawnThread;
    public Setting<Boolean> spawnThreadWhenAttacked;
    public Setting<Boolean> destroyThread;
    public Setting<Boolean> serverThread;
    public Setting<Boolean> gameloop;
    public Setting<Boolean> asyncServerThread;
    public Setting<Boolean> earlyFeetThread;
    public Setting<Boolean> lateBreakThread;
    public Setting<Boolean> motionThread;
    public Setting<Boolean> blockChangeThread;
    public Setting<Integer> priority;
    public Setting<Boolean> spectator;
    public Setting<Boolean> noPacketFlyRotationChecks;
    public Setting<Boolean> clearPost;
    public Setting<Boolean> sequential;
    public Setting<Integer> seqTime;
    public Setting<Boolean> endSequenceOnSpawn;
    public Setting<Boolean> endSequenceOnBreak;
    public Setting<Boolean> endSequenceOnExplosion;
    public Setting<Boolean> antiPlaceFail;
    public Setting<Boolean> debugAntiPlaceFail;
    public Setting<Boolean> alwaysBomb;
    public Setting<Integer> removeTime;
    public final Setting<ColorSetting> boxColor;
    public final Setting<ColorSetting> outLine;
    public final Setting<ColorSetting> indicatorColor;
    public ListenerSound soundObserver;
    public AtomicInteger motionID;
    public Timer renderTimer;
    public Timer bypassTimer;
    public Timer obbyTimer;
    public Timer obbyCalcTimer;
    public Timer targetTimer;
    public Timer cTargetTimer;
    public Timer forceTimer;
    public Timer liquidTimer;
    public Timer shieldTimer;
    public Timer slideTimer;
    public Timer zoomTimer;
    public Timer pullTimer;
    public Queue<Runnable> post;
    public volatile RotationFunction rotation;
    public BlockPos bombPos;
    public EntityPlayer target;
    public Entity crystal;
    public Entity focus;
    public BlockPos renderPos;
    public BlockPos slidePos;
    public boolean switching;
    public boolean isSpoofing;
    public boolean noGod;
    public String damage;
    public ExtrapolationHelper extrapolationHelper;
    public HelperLiquids liquidHelper;
    public HelperPlace placeHelper;
    public HelperBreak breakHelper;
    public HelperObby obbyHelper;
    public HelperBreakMotion breakHelperMotion;
    public HelperEntityBlocksPlace bbBlockingHelper;
    private BlockPos bypassPos;
    private float forward;
    public DamageHelper damageHelper;
    private Timer inv_timer;
    private int prev_crystals_ammount;
    private int crys_speed;
    
    public AutoCrystal() {
        super("AutoCrystal", "\u0441\u0442\u0430\u0432\u0438\u0442 \u0438 \u043b\u043e\u043c\u0430\u0435\u0442 \u043a\u0440\u0438\u0441\u0442\u0430\u043b\u044b", "do you really need-an explanation?)", Category.COMBAT);
        this.placed = new ConcurrentHashMap<BlockPos, CrystalTimeStamp>();
        this.sequentialHelper = new HelperSequential(this);
        this.idHelper = new IDHelper();
        this.rotationHelper = new HelperRotation(this);
        this.rangeHelper = new HelperRange(this);
        this.pitchMouseFilter = new MouseFilter();
        this.yawMouseFilter = new MouseFilter();
        this.fadeList = new HashMap<BlockPos, Long>();
        this.settingType = (Setting<settingtypeEn>)this.register(new Setting("Settings", (T)settingtypeEn.Noob));
        this.page = (Setting<pages>)this.register(new Setting("Page", (T)pages.Place));
        this.extrapol = (Setting<Integer>)this.register(new Setting("Extrapolation", (T)0, (T)0, (T)50, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro));
        this.bExtrapol = (Setting<Integer>)this.register(new Setting("Break-Extrapolation", (T)0, (T)0, (T)50, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro));
        this.blockExtrapol = (Setting<Integer>)this.register(new Setting("Block-Extrapolation", (T)0, (T)0, (T)50, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro));
        this.blockExtraMode = (Setting<BlockExtrapolationMode>)this.register(new Setting("BlockExtraMode", (T)BlockExtrapolationMode.Pessimistic, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker));
        this.doubleExtraCheck = (Setting<Boolean>)this.register(new Setting("DoubleExtraCheck", (T)true, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker));
        this.avgPlaceDamage = (Setting<Boolean>)this.register(new Setting("AvgPlaceExtra", (T)false, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker));
        this.placeExtraWeight = (Setting<Double>)this.register(new Setting("P-Extra-Weight", (T)1.0, (T)0.0, (T)5.0, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro));
        this.placeNormalWeight = (Setting<Double>)this.register(new Setting("P-Norm-Weight", (T)1.0, (T)0.0, (T)5.0, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro));
        this.avgBreakExtra = (Setting<Boolean>)this.register(new Setting("AvgBreakExtra", (T)false, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker));
        this.breakExtraWeight = (Setting<Double>)this.register(new Setting("B-Extra-Weight", (T)1.0, (T)0.0, (T)5.0, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro));
        this.breakNormalWeight = (Setting<Double>)this.register(new Setting("B-Norm-Weight", (T)1.0, (T)0.0, (T)5.0, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro));
        this.gravityExtrapolation = (Setting<Boolean>)this.register(new Setting("Extra-Gravity", (T)true, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker));
        this.gravityFactor = (Setting<Double>)this.register(new Setting("Gravity-Factor", (T)1.0, (T)0.0, (T)5.0, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker));
        this.yPlusFactor = (Setting<Double>)this.register(new Setting("Y-Plus-Factor", (T)1.0, (T)0.0, (T)5.0, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker));
        this.yMinusFactor = (Setting<Double>)this.register(new Setting("Y-Minus-Factor", (T)1.0, (T)0.0, (T)5.0, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Hacker));
        this.selfExtrapolation = (Setting<Boolean>)this.register(new Setting("SelfExtrapolation", (T)false, v -> this.page.getValue() == pages.Extrapolation && this.settingType.getValue() == settingtypeEn.Pro));
        this.useSafetyFactor = (Setting<Boolean>)this.register(new Setting("UseSafetyFactor", (T)false, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.selfFactor = (Setting<Double>)this.register(new Setting("SelfFactor", (T)1.0, (T)0.0, (T)10.0, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.safetyFactor = (Setting<Double>)this.register(new Setting("SafetyFactor", (T)1.0, (T)0.0, (T)10.0, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.compareDiff = (Setting<Double>)this.register(new Setting("CompareDiff", (T)1.0, (T)0.0, (T)10.0, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.facePlaceCompare = (Setting<Boolean>)this.register(new Setting("FacePlaceCompare", (T)false, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.place = (Setting<Boolean>)this.register(new Setting("Place", (T)true, v -> this.page.getValue() == pages.Place));
        this.targetMode = (Setting<Target>)this.register(new Setting("Target", (T)Target.Closest, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro));
        this.placeRange = (Setting<Float>)this.register(new Setting("PlaceRange", (T)6.0f, (T)0.0f, (T)6.0f, v -> this.page.getValue() == pages.Place));
        this.placeTrace = (Setting<Float>)this.register(new Setting("PlaceTrace", (T)6.0f, (T)0.0f, (T)6.0f, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.minDamage = (Setting<Float>)this.register(new Setting("MinDamage", (T)6.0f, (T)0.1f, (T)20.0f, v -> this.page.getValue() == pages.Place));
        this.placeDelay = (Setting<Integer>)this.register(new Setting("PlaceDelay", (T)25, (T)0, (T)500, v -> this.page.getValue() == pages.Place));
        this.placeTimer = new GuardTimer(1000L, 5L).reset(this.placeDelay.getValue());
        this.maxSelfPlace = (Setting<Float>)this.register(new Setting("MaxSelfPlace", (T)9.0f, (T)0.0f, (T)20.0f, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro));
        this.multiPlace = (Setting<Integer>)this.register(new Setting("MultiPlace", (T)1, (T)1, (T)5, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro));
        this.slowPlaceDmg = (Setting<Float>)this.register(new Setting("SlowPlace", (T)4.0f, (T)0.1f, (T)20.0f, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro));
        this.slowPlaceDelay = (Setting<Integer>)this.register(new Setting("SlowPlaceDelay", (T)500, (T)0, (T)500, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro));
        this.override = (Setting<Boolean>)this.register(new Setting("OverridePlace", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.newVer = (Setting<Boolean>)this.register(new Setting("1.13+", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro));
        this.newVerEntities = (Setting<Boolean>)this.register(new Setting("1.13-Entities", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro));
        this.placeSwing = (Setting<SwingTime>)this.register(new Setting("PlaceSwing", (T)SwingTime.Post, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.smartTrace = (Setting<Boolean>)this.register(new Setting("Smart-Trace", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.placeRangeEyes = (Setting<Boolean>)this.register(new Setting("PlaceRangeEyes", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.placeRangeCenter = (Setting<Boolean>)this.register(new Setting("PlaceRangeCenter", (T)true, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.traceWidth = (Setting<Double>)this.register(new Setting("TraceWidth", (T)(-1.0), (T)(-1.0), (T)1.0, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.fallbackTrace = (Setting<Boolean>)this.register(new Setting("Fallback-Trace", (T)true, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.rayTraceBypass = (Setting<Boolean>)this.register(new Setting("RayTraceBypass", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro));
        this.forceBypass = (Setting<Boolean>)this.register(new Setting("ForceBypass", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro));
        this.rayBypassFacePlace = (Setting<Boolean>)this.register(new Setting("RayBypassFacePlace", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.rayBypassFallback = (Setting<Boolean>)this.register(new Setting("RayBypassFallback", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.bypassTicks = (Setting<Integer>)this.register(new Setting("BypassTicks", (T)10, (T)0, (T)20, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.rbYaw = (Setting<Float>)this.register(new Setting("RB-Yaw", (T)180.0f, (T)0.0f, (T)180.0f, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.rbPitch = (Setting<Float>)this.register(new Setting("RB-Pitch", (T)90.0f, (T)0.0f, (T)90.0f, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.bypassRotationTime = (Setting<Integer>)this.register(new Setting("RayBypassRotationTime", (T)500, (T)0, (T)1000, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.ignoreNonFull = (Setting<Boolean>)this.register(new Setting("IgnoreNonFull", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.efficientPlacements = (Setting<Boolean>)this.register(new Setting("EfficientPlacements", (T)false, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Hacker));
        this.simulatePlace = (Setting<Integer>)this.register(new Setting("Simulate-Place", (T)0, (T)0, (T)10, v -> this.page.getValue() == pages.Place && this.settingType.getValue() == settingtypeEn.Pro));
        this.crystalRender = new FakeCrystalRender(this.simulatePlace);
        this.attackMode = (Setting<Attack2>)this.register(new Setting("Attack", (T)Attack2.Crystal, v -> this.page.getValue() == pages.Break));
        this.attack = (Setting<Boolean>)this.register(new Setting("Break", (T)true, v -> this.page.getValue() == pages.Break));
        this.breakRange = (Setting<Float>)this.register(new Setting("BreakRange", (T)6.0f, (T)0.0f, (T)6.0f, v -> this.page.getValue() == pages.Break));
        this.breakDelay = (Setting<Integer>)this.register(new Setting("BreakDelay", (T)25, (T)0, (T)500, v -> this.page.getValue() == pages.Break));
        this.breakTimer = new GuardTimer(1000L, 5L).reset(this.breakDelay.getValue());
        this.breakTrace = (Setting<Float>)this.register(new Setting("BreakTrace", (T)3.0f, (T)0.0f, (T)6.0f, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.minBreakDamage = (Setting<Float>)this.register(new Setting("MinBreakDmg", (T)0.5f, (T)0.0f, (T)20.0f, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.maxSelfBreak = (Setting<Float>)this.register(new Setting("MaxSelfBreak", (T)10.0f, (T)0.0f, (T)20.0f, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.slowBreakDamage = (Setting<Float>)this.register(new Setting("SlowBreak", (T)3.0f, (T)0.1f, (T)20.0f, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.slowBreakDelay = (Setting<Integer>)this.register(new Setting("SlowBreakDelay", (T)500, (T)0, (T)500, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.instant = (Setting<Boolean>)this.register(new Setting("Instant", (T)false, v -> this.page.getValue() == pages.Break));
        this.asyncCalc = (Setting<Boolean>)this.register(new Setting("Async-Calc", (T)false, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.alwaysCalc = (Setting<Boolean>)this.register(new Setting("Always-Calc", (T)false, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.ncpRange = (Setting<Boolean>)this.register(new Setting("NCP-Range", (T)false, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.placeBreakRange = (Setting<SmartRange>)this.register(new Setting("SmartRange", (T)SmartRange.None, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.smartTicks = (Setting<Integer>)this.register(new Setting("SmartRange-Ticks", (T)0, (T)0, (T)20, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.negativeTicks = (Setting<Integer>)this.register(new Setting("Negative-Ticks", (T)0, (T)0, (T)20, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker));
        this.smartBreakTrace = (Setting<Boolean>)this.register(new Setting("SmartBreakTrace", (T)true, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.negativeBreakTrace = (Setting<Boolean>)this.register(new Setting("NegativeBreakTrace", (T)true, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker));
        this.packets = (Setting<Integer>)this.register(new Setting("Packets", (T)1, (T)1, (T)5, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker));
        this.overrideBreak = (Setting<Boolean>)this.register(new Setting("OverrideBreak", (T)false, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker));
        this.antiWeakness = (Setting<AntiWeakness>)this.register(new Setting("AntiWeakness", (T)AntiWeakness.None, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.instantAntiWeak = (Setting<Boolean>)this.register(new Setting("AW-Instant", (T)true, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Pro));
        this.efficient = (Setting<Boolean>)this.register(new Setting("Efficient", (T)true, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker));
        this.manually = (Setting<Boolean>)this.register(new Setting("Manually", (T)true, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker));
        this.manualDelay = (Setting<Integer>)this.register(new Setting("ManualDelay", (T)500, (T)0, (T)500, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker));
        this.breakSwing = (Setting<SwingTime>)this.register(new Setting("BreakSwing", (T)SwingTime.Post, v -> this.page.getValue() == pages.Break && this.settingType.getValue() == settingtypeEn.Hacker));
        this.rotate = (Setting<ACRotate>)this.register(new Setting("Rotate", (T)ACRotate.None, v -> this.page.getValue() == pages.Rotations));
        this.rotateMode = (Setting<RotateMode>)this.register(new Setting("Rotate-Mode", (T)RotateMode.Normal, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro));
        this.smoothSpeed = (Setting<Float>)this.register(new Setting("Smooth-Speed", (T)0.5f, (T)0.1f, (T)2.0f, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro));
        this.endRotations = (Setting<Integer>)this.register(new Setting("End-Rotations", (T)250, (T)0, (T)1000, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker));
        this.angle = (Setting<Float>)this.register(new Setting("Break-Angle", (T)180.0f, (T)0.1f, (T)180.0f, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro));
        this.placeAngle = (Setting<Float>)this.register(new Setting("Place-Angle", (T)180.0f, (T)0.1f, (T)180.0f, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro));
        this.height = (Setting<Float>)this.register(new Setting("Height", (T)0.05f, (T)0.0f, (T)1.0f, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker));
        this.placeHeight = (Setting<Double>)this.register(new Setting("Place-Height", (T)1.0, (T)0.0, (T)1.0, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker));
        this.rotationTicks = (Setting<Integer>)this.register(new Setting("Rotations-Existed", (T)0, (T)0, (T)500, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro));
        this.focusRotations = (Setting<Boolean>)this.register(new Setting("Focus-Rotations", (T)false, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker));
        this.focusAngleCalc = (Setting<Boolean>)this.register(new Setting("FocusRotationCompare", (T)false, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker));
        this.focusExponent = (Setting<Double>)this.register(new Setting("FocusExponent", (T)0.0, (T)0.0, (T)10.0, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker));
        this.focusDiff = (Setting<Double>)this.register(new Setting("FocusDiff", (T)0.0, (T)0.0, (T)180.0, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker));
        this.rotationExponent = (Setting<Double>)this.register(new Setting("RotationExponent", (T)0.0, (T)0.0, (T)10.0, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker));
        this.minRotDiff = (Setting<Double>)this.register(new Setting("MinRotationDiff", (T)0.0, (T)0.0, (T)180.0, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Hacker));
        this.existed = (Setting<Integer>)this.register(new Setting("Existed", (T)0, (T)0, (T)500, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro));
        this.pingExisted = (Setting<Boolean>)this.register(new Setting("Ping-Existed", (T)false, v -> this.page.getValue() == pages.Rotations && this.settingType.getValue() == settingtypeEn.Pro));
        this.targetRange = (Setting<Float>)this.register(new Setting("TargetRange", (T)20.0f, (T)0.1f, (T)20.0f, v -> this.page.getValue() == pages.Misc));
        this.pbTrace = (Setting<Float>)this.register(new Setting("CombinedTrace", (T)3.0f, (T)0.0f, (T)6.0f, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)12.0f, (T)0.1f, (T)20.0f, v -> this.page.getValue() == pages.Misc));
        this.suicide = (Setting<Boolean>)this.register(new Setting("Suicide", (T)false, v -> this.page.getValue() == pages.Misc));
        this.shield = (Setting<Boolean>)this.register(new Setting("Shield", (T)false, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.shieldCount = (Setting<Integer>)this.register(new Setting("ShieldCount", (T)1, (T)1, (T)5, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.shieldMinDamage = (Setting<Float>)this.register(new Setting("ShieldMinDamage", (T)6.0f, (T)0.0f, (T)20.0f, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.shieldSelfDamage = (Setting<Float>)this.register(new Setting("ShieldSelfDamage", (T)2.0f, (T)0.0f, (T)20.0f, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.shieldDelay = (Setting<Integer>)this.register(new Setting("ShieldPlaceDelay", (T)50, (T)0, (T)5000, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.shieldRange = (Setting<Float>)this.register(new Setting("ShieldRange", (T)10.0f, (T)0.0f, (T)20.0f, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.shieldPrioritizeHealth = (Setting<Boolean>)this.register(new Setting("Shield-PrioritizeHealth", (T)false, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.multiTask = (Setting<Boolean>)this.register(new Setting("MultiTask", (T)true, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro));
        this.multiPlaceCalc = (Setting<Boolean>)this.register(new Setting("MultiPlace-Calc", (T)true, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.multiPlaceMinDmg = (Setting<Boolean>)this.register(new Setting("MultiPlace-MinDmg", (T)true, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.countDeadCrystals = (Setting<Boolean>)this.register(new Setting("CountDeadCrystals", (T)false, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.countDeathTime = (Setting<Boolean>)this.register(new Setting("CountWithinDeathTime", (T)false, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.yCalc = (Setting<Boolean>)this.register(new Setting("Y-Calc", (T)false, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.dangerSpeed = (Setting<Boolean>)this.register(new Setting("Danger-Speed", (T)false, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro));
        this.dangerHealth = (Setting<Float>)this.register(new Setting("Danger-Health", (T)0.0f, (T)0.0f, (T)36.0f, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro));
        this.cooldown = (Setting<Integer>)this.register(new Setting("CoolDown", (T)500, (T)0, (T)10000, v -> this.page.getValue() == pages.Misc));
        this.weaknessHelper = new WeaknessHelper(this.antiWeakness, this.cooldown);
        this.placeCoolDown = (Setting<Integer>)this.register(new Setting("PlaceCooldown", (T)0, (T)0, (T)10000, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro));
        this.antiFriendPop = (Setting<AntiFriendPop>)this.register(new Setting("AntiFriendPop", (T)AntiFriendPop.None, v -> this.page.getValue() == pages.Misc));
        this.antiFeetPlace = (Setting<Boolean>)this.register(new Setting("AntiFeetPlace", (T)false, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.feetBuffer = (Setting<Integer>)this.register(new Setting("FeetBuffer", (T)5, (T)0, (T)50, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.serverTimeHelper = new ServerTimeHelper(this, this.rotate, this.placeSwing, this.antiFeetPlace, this.newVer, this.feetBuffer);
        this.stopWhenEating = (Setting<Boolean>)this.register(new Setting("StopWhenEating", (T)false, v -> this.page.getValue() == pages.Misc));
        this.stopWhenMining = (Setting<Boolean>)this.register(new Setting("StopWhenMining", (T)false, v -> this.page.getValue() == pages.Misc));
        this.dangerFacePlace = (Setting<Boolean>)this.register(new Setting("Danger-FacePlace", (T)false, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Pro));
        this.motionCalc = (Setting<Boolean>)this.register(new Setting("Motion-Calc", (T)false, v -> this.page.getValue() == pages.Misc && this.settingType.getValue() == settingtypeEn.Hacker));
        this.holdFacePlace = (Setting<Boolean>)this.register(new Setting("HoldFacePlace", (T)false, v -> this.page.getValue() == pages.FacePlace));
        this.facePlace = (Setting<Float>)this.register(new Setting("FacePlace", (T)10.0f, (T)0.0f, (T)36.0f, v -> this.page.getValue() == pages.FacePlace));
        this.minFaceDmg = (Setting<Float>)this.register(new Setting("Min-FP", (T)2.0f, (T)0.0f, (T)5.0f, v -> this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Pro));
        this.armorPlace = (Setting<Float>)this.register(new Setting("ArmorPlace", (T)5.0f, (T)0.0f, (T)100.0f, v -> this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Pro));
        this.pickAxeHold = (Setting<Boolean>)this.register(new Setting("PickAxe-Hold", (T)false, v -> this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Pro));
        this.antiNaked = (Setting<Boolean>)this.register(new Setting("AntiNaked", (T)false, v -> this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Pro));
        this.fallBack = (Setting<Boolean>)this.register(new Setting("FallBack", (T)true, v -> this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Hacker));
        this.fallBackDiff = (Setting<Float>)this.register(new Setting("Fallback-Difference", (T)10.0f, (T)0.0f, (T)16.0f, v -> this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Hacker));
        this.fallBackDmg = (Setting<Float>)this.register(new Setting("FallBackDmg", (T)3.0f, (T)0.0f, (T)6.0f, v -> this.page.getValue() == pages.FacePlace && this.settingType.getValue() == settingtypeEn.Hacker));
        this.autoSwitch = (Setting<AutoSwitch>)this.register(new Setting("AutoSwitch", (T)AutoSwitch.Bind, v -> this.page.getValue() == pages.SwitchNSwing));
        this.mainHand = (Setting<Boolean>)this.register(new Setting("MainHand", (T)false, v -> this.page.getValue() == pages.SwitchNSwing));
        this.switchBind = (Setting<SubBind>)this.register(new Setting("SwitchBind", (T)new SubBind(0), v -> this.page.getValue() == pages.SwitchNSwing));
        this.switchBack = (Setting<Boolean>)this.register(new Setting("SwitchBack", (T)true, v -> this.page.getValue() == pages.SwitchNSwing));
        this.useAsOffhand = (Setting<Boolean>)this.register(new Setting("UseAsOffHandBind", (T)false, v -> this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Pro));
        this.instantOffhand = (Setting<Boolean>)this.register(new Setting("Instant-Offhand", (T)true, v -> this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Hacker));
        this.switchMessage = (Setting<Boolean>)this.register(new Setting("Switch-Message", (T)false, v -> this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Hacker));
        this.swing = (Setting<SwingType>)this.register(new Setting("BreakHand", (T)SwingType.MainHand, v -> this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Hacker));
        this.placeHand = (Setting<SwingType>)this.register(new Setting("PlaceHand", (T)SwingType.MainHand, v -> this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Hacker));
        this.cooldownBypass = (Setting<CooldownBypass2>)this.register(new Setting("CooldownBypass", (T)CooldownBypass2.None, v -> this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Pro));
        this.obsidianBypass = (Setting<CooldownBypass2>)this.register(new Setting("ObsidianBypass", (T)CooldownBypass2.None, v -> this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Pro));
        this.antiWeaknessBypass = (Setting<CooldownBypass2>)this.register(new Setting("AntiWeaknessBypass", (T)CooldownBypass2.None, v -> this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Pro));
        this.mineBypass = (Setting<CooldownBypass2>)this.register(new Setting("MineBypass", (T)CooldownBypass2.None, v -> this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Pro));
        this.obbyHand = (Setting<SwingType>)this.register(new Setting("ObbyHand", (T)SwingType.MainHand, v -> this.page.getValue() == pages.SwitchNSwing && this.settingType.getValue() == settingtypeEn.Hacker));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.renderTime = (Setting<Integer>)this.register(new Setting("Render-Time", (T)600, (T)0, (T)5000, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.box = (Setting<Boolean>)this.register(new Setting("Draw-Box", (T)true, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.fade = (Setting<Boolean>)this.register(new Setting("Fade", (T)true, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.fadeComp = (Setting<Boolean>)this.register(new Setting("Fade-Compatibility", (T)false, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Hacker));
        this.fadeTime = (Setting<Integer>)this.register(new Setting("Fade-Time", (T)1000, (T)0, (T)5000, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.realtime = (Setting<Boolean>)this.register(new Setting("Realtime", (T)false, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Hacker));
        this.slide = (Setting<Boolean>)this.register(new Setting("Slide", (T)false, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.smoothSlide = (Setting<Boolean>)this.register(new Setting("SmoothenSlide", (T)false, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.slideTime = (Setting<Integer>)this.register(new Setting("Slide-Time", (T)250, (T)1, (T)1000, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Hacker));
        this.zoom = (Setting<Boolean>)this.register(new Setting("Zoom", (T)false, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.zoomTime = (Setting<Double>)this.register(new Setting("Zoom-Time", (T)100.0, (T)1.0, (T)1000.0, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.zoomOffset = (Setting<Double>)this.register(new Setting("Zoom-Offset", (T)(-0.5), (T)(-1.0), (T)1.0, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.multiZoom = (Setting<Boolean>)this.register(new Setting("Multi-Zoom", (T)false, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.renderExtrapolation = (Setting<Boolean>)this.register(new Setting("RenderExtrapolation", (T)false, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Hacker));
        this.renderDamage = (Setting<RenderDamagePos>)this.register(new Setting("DamageRender", (T)RenderDamagePos.None, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.renderMode = (Setting<RenderDamage>)this.register(new Setting("DamageMode", (T)RenderDamage.Normal, v -> this.page.getValue() == pages.Render && this.settingType.getValue() == settingtypeEn.Pro));
        this.setDead = (Setting<Boolean>)this.register(new Setting("SetDead", (T)false, v -> this.page.getValue() == pages.SetDead));
        this.instantSetDead = (Setting<Boolean>)this.register(new Setting("Instant-Dead", (T)false, v -> this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Pro));
        this.pseudoSetDead = (Setting<Boolean>)this.register(new Setting("Pseudo-Dead", (T)true, v -> this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Pro));
        this.simulateExplosion = (Setting<Boolean>)this.register(new Setting("SimulateExplosion", (T)false, v -> this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Hacker));
        this.soundRemove = (Setting<Boolean>)this.register(new Setting("SoundRemove", (T)true, v -> this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Pro));
        this.useSafeDeathTime = (Setting<Boolean>)this.register(new Setting("UseSafeDeathTime", (T)false, v -> this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Hacker));
        this.safeDeathTime = (Setting<Integer>)this.register(new Setting("Safe-Death-Time", (T)0, (T)0, (T)500, v -> this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Hacker));
        this.deathTime = (Setting<Integer>)this.register(new Setting("Death-Time", (T)0, (T)0, (T)500, v -> this.page.getValue() == pages.SetDead && this.settingType.getValue() == settingtypeEn.Pro));
        this.obsidian = (Setting<Boolean>)this.register(new Setting("Obsidian", (T)false, v -> this.page.getValue() == pages.Obsidian));
        this.basePlaceOnly = (Setting<Boolean>)this.register(new Setting("BasePlaceOnly", (T)false, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro));
        this.obbySwitch = (Setting<Boolean>)this.register(new Setting("Obby-Switch", (T)false, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro));
        this.obbyDelay = (Setting<Integer>)this.register(new Setting("ObbyDelay", (T)500, (T)0, (T)5000, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro));
        this.obbyCalc = (Setting<Integer>)this.register(new Setting("ObbyCalc", (T)500, (T)0, (T)5000, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro));
        this.helpingBlocks = (Setting<Integer>)this.register(new Setting("HelpingBlocks", (T)1, (T)0, (T)5, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro));
        this.obbyMinDmg = (Setting<Float>)this.register(new Setting("Obby-MinDamage", (T)7.0f, (T)0.1f, (T)36.0f, v -> this.page.getValue() == pages.Obsidian));
        this.terrainCalc = (Setting<Boolean>)this.register(new Setting("TerrainCalc", (T)true, v -> this.page.getValue() == pages.Obsidian));
        this.obbySafety = (Setting<Boolean>)this.register(new Setting("ObbySafety", (T)false, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker));
        this.obbyTrace = (Setting<RayTraceMode>)this.register(new Setting("Obby-Raytrace", (T)RayTraceMode.Fast, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro));
        this.obbyTerrain = (Setting<Boolean>)this.register(new Setting("Obby-Terrain", (T)true, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro));
        this.obbyPreSelf = (Setting<Boolean>)this.register(new Setting("Obby-PreSelf", (T)true, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro));
        this.fastObby = (Setting<Integer>)this.register(new Setting("Fast-Obby", (T)0, (T)0, (T)3, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Pro));
        this.maxDiff = (Setting<Integer>)this.register(new Setting("Max-Difference", (T)1, (T)0, (T)5, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker));
        this.maxDmgDiff = (Setting<Double>)this.register(new Setting("Max-DamageDiff", (T)0.0, (T)0.0, (T)10.0, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker));
        this.setState = (Setting<Boolean>)this.register(new Setting("Client-Blocks", (T)false, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker));
        this.obbySwing = (Setting<PlaceSwing>)this.register(new Setting("Obby-Swing", (T)PlaceSwing.Once, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker));
        this.obbyFallback = (Setting<Boolean>)this.register(new Setting("Obby-Fallback", (T)false, v -> this.page.getValue() == pages.Obsidian && this.settingType.getValue() == settingtypeEn.Hacker));
        this.obbyRotate = (Setting<Rotate>)this.register(new Setting("Obby-Rotate", (T)Rotate.None, v -> this.page.getValue() == pages.Obsidian));
        this.interact = (Setting<Boolean>)this.register(new Setting("Interact", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro));
        this.inside = (Setting<Boolean>)this.register(new Setting("Inside", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro));
        this.lava = (Setting<Boolean>)this.register(new Setting("Lava", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro));
        this.water = (Setting<Boolean>)this.register(new Setting("Water", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro));
        this.liquidObby = (Setting<Boolean>)this.register(new Setting("LiquidObby", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro));
        this.liquidRayTrace = (Setting<Boolean>)this.register(new Setting("LiquidRayTrace", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Hacker));
        this.liqDelay = (Setting<Integer>)this.register(new Setting("LiquidDelay", (T)500, (T)0, (T)1000, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro));
        this.liqRotate = (Setting<Rotate>)this.register(new Setting("LiquidRotate", (T)Rotate.None, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro));
        this.pickaxeOnly = (Setting<Boolean>)this.register(new Setting("PickaxeOnly", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro));
        this.interruptSpeedmine = (Setting<Boolean>)this.register(new Setting("InterruptSpeedmine", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro));
        this.setAir = (Setting<Boolean>)this.register(new Setting("SetAir", (T)true, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Pro));
        this.absorb = (Setting<Boolean>)this.register(new Setting("Absorb", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Hacker));
        this.requireOnGround = (Setting<Boolean>)this.register(new Setting("RequireOnGround", (T)true, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Hacker));
        this.ignoreLavaItems = (Setting<Boolean>)this.register(new Setting("IgnoreLavaItems", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Hacker));
        this.sponges = (Setting<Boolean>)this.register(new Setting("Sponges", (T)false, v -> this.page.getValue() == pages.Liquid && this.settingType.getValue() == settingtypeEn.Hacker));
        this.antiTotem = (Setting<Boolean>)this.register(new Setting("AntiTotem", (T)false, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Pro));
        this.totemHealth = (Setting<Float>)this.register(new Setting("Totem-Health", (T)1.5f, (T)0.0f, (T)10.0f, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.antiTotemHelper = new AntiTotemHelper(this.totemHealth);
        this.minTotemOffset = (Setting<Float>)this.register(new Setting("Min-Offset", (T)0.5f, (T)0.0f, (T)5.0f, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.maxTotemOffset = (Setting<Float>)this.register(new Setting("Max-Offset", (T)2.0f, (T)0.0f, (T)5.0f, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.popDamage = (Setting<Float>)this.register(new Setting("Pop-Damage", (T)12.0f, (T)10.0f, (T)20.0f, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.totemSync = (Setting<Boolean>)this.register(new Setting("TotemSync", (T)true, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.forceAntiTotem = (Setting<Boolean>)this.register(new Setting("Force-AntiTotem", (T)false, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.forceSlow = (Setting<Boolean>)this.register(new Setting("Force-Slow", (T)false, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.syncForce = (Setting<Boolean>)this.register(new Setting("Sync-Force", (T)true, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.dangerForce = (Setting<Boolean>)this.register(new Setting("Danger-Force", (T)false, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.forcePlaceConfirm = (Setting<Integer>)this.register(new Setting("Force-Place", (T)100, (T)0, (T)500, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.forceBreakConfirm = (Setting<Integer>)this.register(new Setting("Force-Break", (T)100, (T)0, (T)500, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.attempts = (Setting<Integer>)this.register(new Setting("Attempts", (T)500, (T)0, (T)10000, v -> this.page.getValue() == pages.AntiTotem && this.settingType.getValue() == settingtypeEn.Hacker));
        this.damageSync = (Setting<Boolean>)this.register(new Setting("DamageSync", (T)false, v -> this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Pro));
        this.preSynCheck = (Setting<Boolean>)this.register(new Setting("Pre-SyncCheck", (T)false, v -> this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Hacker));
        this.discreteSync = (Setting<Boolean>)this.register(new Setting("Discrete-Sync", (T)false, v -> this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Hacker));
        this.dangerSync = (Setting<Boolean>)this.register(new Setting("Danger-Sync", (T)false, v -> this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Pro));
        this.placeConfirm = (Setting<Integer>)this.register(new Setting("Place-Confirm", (T)250, (T)0, (T)500, v -> this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Hacker));
        this.breakConfirm = (Setting<Integer>)this.register(new Setting("Break-Confirm", (T)250, (T)0, (T)500, v -> this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Hacker));
        this.syncDelay = (Setting<Integer>)this.register(new Setting("SyncDelay", (T)500, (T)0, (T)500, v -> this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Pro));
        this.damageSyncHelper = new DamageSyncHelper(this.discreteSync, this.syncDelay, this.dangerSync);
        this.forceHelper = new ForceAntiTotemHelper(this.discreteSync, this.syncDelay, this.forcePlaceConfirm, this.forceBreakConfirm, this.dangerForce);
        this.surroundSync = (Setting<Boolean>)this.register(new Setting("SurroundSync", (T)true, v -> this.page.getValue() == pages.DamageSync && this.settingType.getValue() == settingtypeEn.Hacker));
        this.idPredict = (Setting<Boolean>)this.register(new Setting("ID-Predict", (T)false, v -> this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Pro));
        this.idOffset = (Setting<Integer>)this.register(new Setting("ID-Offset", (T)1, (T)1, (T)10, v -> this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker));
        this.idDelay = (Setting<Integer>)this.register(new Setting("ID-Delay", (T)0, (T)0, (T)500, v -> this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker));
        this.idPackets = (Setting<Integer>)this.register(new Setting("ID-Packets", (T)1, (T)1, (T)10, v -> this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker));
        this.godAntiTotem = (Setting<Boolean>)this.register(new Setting("God-AntiTotem", (T)false, v -> this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker));
        this.holdingCheck = (Setting<Boolean>)this.register(new Setting("Holding-Check", (T)true, v -> this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker));
        this.toolCheck = (Setting<Boolean>)this.register(new Setting("Tool-Check", (T)true, v -> this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker));
        this.godSwing = (Setting<PlaceSwing>)this.register(new Setting("God-Swing", (T)PlaceSwing.Once, v -> this.page.getValue() == pages.Predict && this.settingType.getValue() == settingtypeEn.Hacker));
        this.preCalc = (Setting<PreCalc>)this.register(new Setting("Pre-Calc", (T)PreCalc.None, v -> this.page.getValue() == pages.Efficiency && this.settingType.getValue() == settingtypeEn.Hacker));
        this.preCalcExtra = (Setting<ExtrapolationType>)this.register(new Setting("PreCalcExtra", (T)ExtrapolationType.Place, v -> this.page.getValue() == pages.Efficiency && this.settingType.getValue() == settingtypeEn.Hacker));
        this.preCalcDamage = (Setting<Float>)this.register(new Setting("Pre-CalcDamage", (T)15.0f, (T)0.0f, (T)36.0f, v -> this.page.getValue() == pages.Efficiency && this.settingType.getValue() == settingtypeEn.Hacker));
        this.multiThread = (Setting<Boolean>)this.register(new Setting("MultiThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.smartPost = (Setting<Boolean>)this.register(new Setting("Smart-Post", (T)true, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.mainThreadThreads = (Setting<Boolean>)this.register(new Setting("MainThreadThreads", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.rotationThread = (Setting<RotationThread>)this.register(new Setting("RotationThread", (T)RotationThread.Predict, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.partial = (Setting<Float>)this.register(new Setting("Partial", (T)0.8f, (T)0.0f, (T)1.0f, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.maxCancel = (Setting<Integer>)this.register(new Setting("MaxCancel", (T)10, (T)1, (T)50, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.rotationCanceller = new RotationCanceller(this, this.maxCancel);
        this.timeOut = (Setting<Integer>)this.register(new Setting("Wait", (T)2, (T)1, (T)10, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.blockDestroyThread = (Setting<Boolean>)this.register(new Setting("BlockDestroyThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.threadDelay = (Setting<Integer>)this.register(new Setting("ThreadDelay", (T)25, (T)0, (T)100, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.threadHelper = new ThreadHelper(this, this.multiThread, this.mainThreadThreads, this.threadDelay, this.rotationThread, this.rotate);
        this.tickThreshold = (Setting<Integer>)this.register(new Setting("TickThreshold", (T)5, (T)1, (T)20, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.preSpawn = (Setting<Integer>)this.register(new Setting("PreSpawn", (T)3, (T)1, (T)20, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.maxEarlyThread = (Setting<Integer>)this.register(new Setting("MaxEarlyThread", (T)8, (T)1, (T)20, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.pullBasedDelay = (Setting<Integer>)this.register(new Setting("PullBasedDelay", (T)0, (T)0, (T)1000, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.explosionThread = (Setting<Boolean>)this.register(new Setting("ExplosionThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.soundThread = (Setting<Boolean>)this.register(new Setting("SoundThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.entityThread = (Setting<Boolean>)this.register(new Setting("EntityThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.spawnThread = (Setting<Boolean>)this.register(new Setting("SpawnThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.spawnThreadWhenAttacked = (Setting<Boolean>)this.register(new Setting("SpawnThreadWhenAttacked", (T)true, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.destroyThread = (Setting<Boolean>)this.register(new Setting("DestroyThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.serverThread = (Setting<Boolean>)this.register(new Setting("ServerThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.gameloop = (Setting<Boolean>)this.register(new Setting("Gameloop", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.asyncServerThread = (Setting<Boolean>)this.register(new Setting("AsyncServerThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.earlyFeetThread = (Setting<Boolean>)this.register(new Setting("EarlyFeetThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.lateBreakThread = (Setting<Boolean>)this.register(new Setting("LateBreakThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Hacker));
        this.motionThread = (Setting<Boolean>)this.register(new Setting("MotionThread", (T)true, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.blockChangeThread = (Setting<Boolean>)this.register(new Setting("BlockChangeThread", (T)false, v -> this.page.getValue() == pages.MultiThreading && this.settingType.getValue() == settingtypeEn.Pro));
        this.priority = (Setting<Integer>)this.register(new Setting("Priority", (T)1500, (T)Integer.MIN_VALUE, (T)Integer.MAX_VALUE, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.spectator = (Setting<Boolean>)this.register(new Setting("Spectator", (T)false, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.noPacketFlyRotationChecks = (Setting<Boolean>)this.register(new Setting("NoPacketFlyRotationChecks", (T)true, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.clearPost = (Setting<Boolean>)this.register(new Setting("ClearPost", (T)true, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.sequential = (Setting<Boolean>)this.register(new Setting("Sequential", (T)false, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.seqTime = (Setting<Integer>)this.register(new Setting("Seq-Time", (T)250, (T)0, (T)1000, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.endSequenceOnSpawn = (Setting<Boolean>)this.register(new Setting("EndSequenceOnSpawn", (T)false, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.endSequenceOnBreak = (Setting<Boolean>)this.register(new Setting("EndSequenceOnBreak", (T)false, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.endSequenceOnExplosion = (Setting<Boolean>)this.register(new Setting("EndSequenceOnExplosion", (T)true, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.antiPlaceFail = (Setting<Boolean>)this.register(new Setting("AntiPlaceFail", (T)false, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.debugAntiPlaceFail = (Setting<Boolean>)this.register(new Setting("DebugAntiPlaceFail", (T)false, v -> this.page.getValue() == pages.Dev));
        this.alwaysBomb = (Setting<Boolean>)this.register(new Setting("Always-Bomb", (T)false, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.removeTime = (Setting<Integer>)this.register(new Setting("Remove-Time", (T)1000, (T)0, (T)2500, v -> this.page.getValue() == pages.Dev && this.settingType.getValue() == settingtypeEn.Hacker));
        this.boxColor = (Setting<ColorSetting>)this.register(new Setting("Box", (T)new ColorSetting(1354711231)));
        this.outLine = (Setting<ColorSetting>)this.register(new Setting("Outline", (T)new ColorSetting(1354711231)));
        this.indicatorColor = (Setting<ColorSetting>)this.register(new Setting("IndicatorColor", (T)new ColorSetting(1354711231)));
        this.soundObserver = new ListenerSound(this);
        this.motionID = new AtomicInteger();
        this.renderTimer = new Timer();
        this.bypassTimer = new Timer();
        this.obbyTimer = new Timer();
        this.obbyCalcTimer = new Timer();
        this.targetTimer = new Timer();
        this.cTargetTimer = new Timer();
        this.forceTimer = new Timer();
        this.liquidTimer = new Timer();
        this.shieldTimer = new Timer();
        this.slideTimer = new Timer();
        this.zoomTimer = new Timer();
        this.pullTimer = new Timer();
        this.post = new ConcurrentLinkedQueue<Runnable>();
        this.extrapolationHelper = new ExtrapolationHelper(this);
        this.liquidHelper = new HelperLiquids(this);
        this.placeHelper = new HelperPlace(this);
        this.breakHelper = new HelperBreak(this);
        this.obbyHelper = new HelperObby(this);
        this.breakHelperMotion = new HelperBreakMotion(this);
        this.bbBlockingHelper = new HelperEntityBlocksPlace(this);
        this.forward = 0.004f;
        this.damageHelper = new DamageHelper(this, this.extrapolationHelper, this.terrainCalc, this.extrapol, this.bExtrapol, this.selfExtrapolation, this.obbyTerrain);
        this.inv_timer = new Timer();
    }
    
    public static boolean canBeFeetPlaced(final EntityPlayer player, final boolean ignoreCrystals, final boolean noBoost2) {
        final BlockPos origin = player.getPosition().down();
        for (final EnumFacing face : EnumFacing.HORIZONTALS) {
            final BlockPos off = origin.offset(face);
            final IBlockState state = AutoCrystal.mc.world.getBlockState(off);
            if (ServerTimeHelper.canPlaceCrystal(off, ignoreCrystals, noBoost2)) {
                return true;
            }
            final BlockPos off2 = off.offset(face);
            if (ServerTimeHelper.canPlaceCrystal(off2, ignoreCrystals, noBoost2) && state.getBlock() == Blocks.AIR) {
                return true;
            }
        }
        return false;
    }
    
    public static EntityPlayer getByFov(final List<EntityPlayer> players, final double maxRange) {
        EntityPlayer closest = null;
        double closestAngle = 360.0;
        for (final EntityPlayer player : players) {
            if (!ServerTimeHelper.isValid((Entity)player, maxRange)) {
                continue;
            }
            final double angle = RotationUtil.getAngle((Entity)player, 1.4);
            if (angle >= closestAngle || angle >= AutoCrystal.mc.gameSettings.fovSetting / 2.0f) {
                continue;
            }
            closest = player;
            closestAngle = angle;
        }
        return closest;
    }
    
    public static EntityPlayer getByAngle(final List<EntityPlayer> players, final double maxRange) {
        EntityPlayer closest = null;
        double closestAngle = 360.0;
        for (final EntityPlayer player : players) {
            if (!ServerTimeHelper.isValid((Entity)player, maxRange)) {
                continue;
            }
            final double angle = RotationUtil.getAngle((Entity)player, 1.4);
            if (angle >= closestAngle || angle >= AutoCrystal.mc.gameSettings.fovSetting / 2.0f) {
                continue;
            }
            closest = player;
            closestAngle = angle;
        }
        return closest;
    }
    
    public static AxisAlignedBB interpolatePos(final BlockPos pos, final float height) {
        return new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + height), (double)(pos.getZ() + 1));
    }
    
    public boolean isNotCheckingRotations() {
        return this.noPacketFlyRotationChecks.getValue() && ((PacketFly)Thunderhack.moduleManager.getModuleByClass((Class)PacketFly.class)).isEnabled();
    }
    
    @Override
    public void onEnable() {
        this.resetModule();
        Thunderhack.setDeadManager.addObserver(this.soundObserver);
    }
    
    @Override
    public void onDisable() {
        Thunderhack.setDeadManager.removeObserver(this.soundObserver);
        this.resetModule();
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.switching) {
            return ChatFormatting.GREEN + "Switching";
        }
        final String cps = this.crys_speed * 2 + " c/s";
        final EntityPlayer t = this.getTarget();
        return (t == null) ? null : (t.getName() + " " + cps);
    }
    
    public void setRenderPos(final BlockPos pos, final float damage) {
        this.setRenderPos(pos, MathUtil.round(damage, 1) + "");
    }
    
    public void setRenderPos(final BlockPos pos, final String text) {
        this.renderTimer.reset();
        if (pos != null && !pos.equals((Object)this.slidePos) && (!this.smoothSlide.getValue() || this.slideTimer.passedMs(this.slideTime.getValue()))) {
            this.slidePos = this.renderPos;
            this.slideTimer.reset();
        }
        if (pos != null && (this.multiZoom.getValue() || !pos.equals((Object)this.renderPos))) {
            this.zoomTimer.reset();
        }
        this.renderPos = pos;
        this.damage = text;
        this.bypassPos = null;
    }
    
    public BlockPos getRenderPos() {
        if (this.renderTimer.passedMs(this.renderTime.getValue())) {
            this.renderPos = null;
            this.slidePos = null;
        }
        return this.renderPos;
    }
    
    public EntityPlayer getTarget() {
        if (this.targetTimer.passedMs(600L)) {
            this.target = null;
        }
        return this.target;
    }
    
    public void setTarget(final EntityPlayer target) {
        this.targetTimer.reset();
        this.target = target;
    }
    
    public Entity getCrystal() {
        if (this.cTargetTimer.passedMs(600L)) {
            this.crystal = null;
        }
        return this.crystal;
    }
    
    public void setCrystal(final Entity crystal) {
        if (this.focusRotations.getValue() && !this.noRotateNigga(ACRotate.Break)) {
            this.focus = crystal;
        }
        this.cTargetTimer.reset();
        this.crystal = crystal;
    }
    
    public float getMinDamage() {
        return ((this.holdFacePlace.getValue() && AutoCrystal.mc.currentScreen == null && Mouse.isButtonDown(0) && (!(AutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) || this.pickAxeHold.getValue())) || this.dangerFacePlace.getValue()) ? this.minFaceDmg.getValue() : ((float)this.minDamage.getValue());
    }
    
    public void runPost() {
        CollectionUtil.emptyQueue(this.post);
    }
    
    public void resetModule() {
        this.target = null;
        this.crystal = null;
        this.renderPos = null;
        this.slidePos = null;
        this.rotation = null;
        this.switching = false;
        this.bypassPos = null;
        this.post.clear();
        AutoCrystal.mc.addScheduledTask(this.crystalRender::clear);
        try {
            this.placed.clear();
            this.threadHelper.resetThreadHelper();
            this.rotationCanceller.reset();
            this.antiTotemHelper.setTarget(null);
            this.antiTotemHelper.setTargetPos(null);
            this.idHelper.setUpdated(false);
            this.idHelper.setHighestID(0);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public boolean shouldDanger() {
        return this.dangerSpeed.getValue() && EntityUtil.getHealth((Entity)AutoCrystal.mc.player) < this.dangerHealth.getValue();
    }
    
    public void checkExecutor() {
        if (!AutoCrystal.started && this.asyncServerThread.getValue() && this.serverThread.getValue() && this.multiThread.getValue() && this.rotate.getValue() == ACRotate.None) {
            synchronized (AutoCrystal.class) {
                if (!AutoCrystal.ATOMIC_STARTED.get()) {
                    this.startExecutor();
                    AutoCrystal.ATOMIC_STARTED.set(true);
                    AutoCrystal.started = true;
                }
            }
        }
    }
    
    private void startExecutor() {
        AutoCrystal.EXECUTOR.scheduleAtFixedRate(this::doExecutorTick, 0L, 1L, TimeUnit.MILLISECONDS);
    }
    
    private void doExecutorTick() {
        if (AutoCrystal.mc.player != null && AutoCrystal.mc.world != null && this.asyncServerThread.getValue() && this.rotate.getValue() == ACRotate.None && this.serverThread.getValue() && this.multiThread.getValue()) {
            if (Thunderhack.servtickManager.valid(Thunderhack.servtickManager.getTickTimeAdjusted(), Thunderhack.servtickManager.normalize(Thunderhack.servtickManager.getSpawnTime() - this.tickThreshold.getValue()), Thunderhack.servtickManager.normalize(Thunderhack.servtickManager.getSpawnTime() - this.preSpawn.getValue()))) {
                if (!this.earlyFeetThread.getValue()) {
                    this.threadHelper.startThread(new BlockPos[0]);
                }
                else if (this.lateBreakThread.getValue()) {
                    this.threadHelper.startThread(true, false, new BlockPos[0]);
                }
            }
            else {
                final EntityPlayer closest = ServerTimeHelper.getClosestEnemy();
                if (closest != null && ServerTimeHelper.isSemiSafe(closest, true, this.newVer.getValue()) && canBeFeetPlaced(closest, true, this.newVer.getValue()) && this.earlyFeetThread.getValue() && Thunderhack.servtickManager.valid(Thunderhack.servtickManager.getTickTimeAdjusted(), 0, this.maxEarlyThread.getValue())) {
                    this.threadHelper.startThread(false, true, new BlockPos[0]);
                }
            }
        }
    }
    
    public boolean isSuicideModule() {
        return false;
    }
    
    public BlockPos getBypassPos() {
        if (this.bypassTimer.passedMs(this.bypassRotationTime.getValue()) || !this.forceBypass.getValue() || !this.rayTraceBypass.getValue()) {
            this.bypassPos = null;
        }
        return this.bypassPos;
    }
    
    public void setBypassPos(final BlockPos pos) {
        this.bypassTimer.reset();
        this.bypassPos = pos;
    }
    
    public boolean isEating() {
        final ItemStack stack = AutoCrystal.mc.player.getActiveItemStack();
        return AutoCrystal.mc.player.isHandActive() && !stack.isEmpty() && stack.getItem().getItemUseAction(stack) == EnumAction.EAT;
    }
    
    public boolean isMining() {
        return AutoCrystal.mc.playerController.getIsHittingBlock();
    }
    
    public boolean isOutsidePlaceRange(final BlockPos pos) {
        final EntityPlayer player = (EntityPlayer)AutoCrystal.mc.player;
        final double x = player.posX;
        final double y = player.posY + (this.placeRangeEyes.getValue() ? player.getEyeHeight() : 0.0f);
        final double z = player.posZ;
        final double distance = this.placeRangeCenter.getValue() ? pos.distanceSqToCenter(x, y, z) : pos.distanceSq(x, y, z);
        return distance >= MathUtil.square(this.placeRange.getValue());
    }
    
    public int getDeathTime() {
        if (this.useSafeDeathTime.getValue()) {
            return this.safeDeathTime.getValue();
        }
        if (!this.pseudoSetDead.getValue() && !this.setDead.getValue()) {
            return 0;
        }
        return this.deathTime.getValue();
    }
    
    public boolean noRotateNigga(final ACRotate rotate2) {
        switch (this.rotate.getValue()) {
            case None: {
                return true;
            }
            case All: {
                return false;
            }
            case Place: {
                return rotate2 == ACRotate.Break || rotate2 == ACRotate.None;
            }
            case Break: {
                return rotate2 == ACRotate.Place || rotate2 == ACRotate.None;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean shouldCalcFuckinBitch(final AntiFriendPop type) {
        switch (this.antiFriendPop.getValue()) {
            case None: {
                return false;
            }
            case All: {
                return true;
            }
            case Break: {
                return type == AntiFriendPop.Break;
            }
            case Place: {
                return type == AntiFriendPop.Place;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean shouldcalcN() {
        switch (this.attackMode.getValue()) {
            case Calc: {
                return true;
            }
            case Always: {
                return true;
            }
            case Crystal: {
                return InventoryUtil.isHolding(Items.END_CRYSTAL);
            }
            default: {
                return true;
            }
        }
    }
    
    public boolean shouldattackN() {
        switch (this.attackMode.getValue()) {
            case Calc: {
                return InventoryUtil.isHolding(Items.END_CRYSTAL);
            }
            case Always: {
                return true;
            }
            case Crystal: {
                return InventoryUtil.isHolding(Items.END_CRYSTAL);
            }
            default: {
                return true;
            }
        }
    }
    
    public boolean isOutsideBreakRange(final double x, final double y, final double z, final AutoCrystal module) {
        switch (this.placeBreakRange.getValue()) {
            case All: {
                return !module.rangeHelper.isCrystalInRange(x, y, z, module.smartTicks.getValue()) && !module.rangeHelper.isCrystalInRange(x, y, z, 0);
            }
            case None: {
                return false;
            }
            case Normal: {
                return !module.rangeHelper.isCrystalInRange(x, y, z, 0);
            }
            case Extrapolated: {
                return !module.rangeHelper.isCrystalInRange(x, y, z, module.smartTicks.getValue());
            }
            default: {
                return !module.rangeHelper.isCrystalInRange(x, y, z, module.smartTicks.getValue()) && !module.rangeHelper.isCrystalInRange(x, y, z, 0);
            }
        }
    }
    
    public boolean isOutsideBreakRange(final BlockPos pos, final AutoCrystal module) {
        return this.isOutsideBreakRange(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, module);
    }
    
    @SubscribeEvent
    public void onBoobs(final UpdateEntitiesEvent e) {
        ExtrapolationHelper.onUpdateEntity(e);
    }
    
    @Override
    public void onLogin() {
        this.resetModule();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (fullNullCheck()) {
            return;
        }
        this.threadHelper.schedulePacket(e);
        if (e.getPacket() instanceof SPacketBlockChange && this.multiThread.getValue() && this.blockChangeThread.getValue()) {
            final SPacketBlockChange packet = (SPacketBlockChange)e.getPacket();
            if (packet.getBlockState().getBlock() == Blocks.AIR && AutoCrystal.mc.player.getDistanceSq(packet.getBlockPosition()) < 40.0) {
                final SPacketBlockChange sPacketBlockChange;
                e.addPostEvent(() -> {
                    if (AutoCrystal.mc.world != null && HelperUtil.validChange(sPacketBlockChange.getBlockPosition(), AutoCrystal.mc.world.playerEntities)) {
                        this.threadHelper.startThread(new BlockPos[0]);
                    }
                    return;
                });
            }
        }
        if (e.getPacket() instanceof SPacketMultiBlockChange && this.multiThread.getValue() && this.blockChangeThread.getValue()) {
            final SPacketMultiBlockChange packet2 = (SPacketMultiBlockChange)e.getPacket();
            final SPacketMultiBlockChange.BlockUpdateData[] array;
            final int length;
            int i = 0;
            SPacketMultiBlockChange.BlockUpdateData data;
            e.addPostEvent(() -> {
                packet2.getChangedBlocks();
                length = array.length;
                while (i < length) {
                    data = array[i];
                    if (data.getBlockState().getMaterial() == Material.AIR && HelperUtil.validChange(data.getPos(), AutoCrystal.mc.world.playerEntities)) {
                        this.threadHelper.startThread(new BlockPos[0]);
                        break;
                    }
                    else {
                        ++i;
                    }
                }
                return;
            });
        }
        if (e.getPacket() instanceof SPacketDestroyEntities && this.destroyThread.getValue()) {
            this.threadHelper.schedulePacket(e);
        }
        if (e.getPacket() instanceof SPacketEntity.S15PacketEntityRelMove) {
            this.onEvent22((SPacketEntity)e.getPacket());
        }
        if (e.getPacket() instanceof SPacketEntity.S17PacketEntityLookMove) {
            this.onEvent22((SPacketEntity)e.getPacket());
        }
        if (e.getPacket() instanceof SPacketExplosion && this.explosionThread.getValue() && !((SPacketExplosion)e.getPacket()).getAffectedBlockPositions().isEmpty()) {
            this.threadHelper.schedulePacket(e);
        }
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            this.rotationCanceller.drop();
        }
        if (e.getPacket() instanceof SPacketSpawnObject) {
            try {
                this.onEvent33(e);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
        if (e.getPacket() instanceof SPacketSoundEffect && this.soundThread.getValue()) {
            this.threadHelper.startThread(new BlockPos[0]);
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            this.updater228(event);
        }
        if (event.getPacket() instanceof CPacketPlayer.Position) {
            this.updater228(event);
        }
        if (event.getPacket() instanceof CPacketPlayer.Rotation) {
            this.updater228(event);
        }
        if (event.getPacket() instanceof CPacketPlayer.PositionRotation) {
            this.updater228(event);
        }
    }
    
    @SubscribeEvent
    public void onPacketSendPost(final PacketEvent.SendPost event) {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && this.idPredict.getValue() && !this.noGod && this.breakTimer.passed(this.breakDelay.getValue()) && AutoCrystal.mc.player.getHeldItem(((CPacketPlayerTryUseItemOnBlock)event.getPacket()).getHand()).getItem() == Items.END_CRYSTAL && this.idHelper.isSafe(AutoCrystal.mc.world.playerEntities, this.holdingCheck.getValue(), this.toolCheck.getValue())) {
            this.idHelper.attack(this.breakSwing.getValue(), this.godSwing.getValue(), this.idOffset.getValue(), this.idPackets.getValue(), this.idDelay.getValue());
            this.breakTimer.reset(this.breakDelay.getValue());
        }
        if (event.getPacket() instanceof CPacketUseEntity) {
            Entity entity = null;
            if (entity == null) {
                entity = ((CPacketUseEntity)event.getPacket()).getEntityFromWorld((World)AutoCrystal.mc.world);
                if (entity == null) {
                    return;
                }
            }
            this.serverTimeHelper.onUseEntity((CPacketUseEntity)event.getPacket(), entity);
        }
    }
    
    @Override
    public void onTick() {
        if (this.inv_timer.passedMs(500L)) {
            this.crys_speed = this.prev_crystals_ammount - InventoryUtil.getItemCount(Items.END_CRYSTAL);
            this.prev_crystals_ammount = InventoryUtil.getItemCount(Items.END_CRYSTAL);
            this.inv_timer.reset();
        }
        this.checkExecutor();
        this.placed.values().removeIf(stamp -> System.currentTimeMillis() - stamp.getTimeStamp() > this.removeTime.getValue());
        this.crystalRender.tick();
        if (!this.idHelper.isUpdated()) {
            this.idHelper.update();
            this.idHelper.setUpdated(true);
        }
        this.weaknessHelper.updateWeakness();
    }
    
    private void updater228(final PacketEvent.Send event) {
        if (this.multiThread.getValue() && !this.isSpoofing && this.rotate.getValue() != ACRotate.None && this.rotationThread.getValue() == RotationThread.Cancel) {
            this.rotationCanceller.onPacketNigger(event);
        }
        else {
            this.rotationCanceller.reset();
        }
    }
    
    @SubscribeEvent
    public void onDestroyBlock(final DestroyBlockEvent event) {
        if (this.blockDestroyThread.getValue() && this.multiThread.getValue() && !event.isCanceled() && HelperUtil.validChange(event.getBlockPos(), AutoCrystal.mc.world.playerEntities)) {
            this.threadHelper.startThread(event.getBlockPos().down());
        }
    }
    
    @SubscribeEvent
    public void onGameZaloop(final GameZaloopEvent event) {
        this.rotationCanceller.onGameLoop();
        if (!this.multiThread.getValue()) {
            return;
        }
        if (this.gameloop.getValue()) {
            this.threadHelper.startThread(new BlockPos[0]);
        }
        else if (this.rotate.getValue() != ACRotate.None && this.rotationThread.getValue() == RotationThread.Predict && AutoCrystal.mc.getRenderPartialTicks() >= this.partial.getValue()) {
            this.threadHelper.startThread(new BlockPos[0]);
        }
        else if (this.rotate.getValue() == ACRotate.None && this.serverThread.getValue() && AutoCrystal.mc.world != null && AutoCrystal.mc.player != null) {
            if (Thunderhack.servtickManager.valid(Thunderhack.servtickManager.getTickTimeAdjusted(), Thunderhack.servtickManager.normalize(Thunderhack.servtickManager.getSpawnTime() - this.tickThreshold.getValue()), Thunderhack.servtickManager.normalize(Thunderhack.servtickManager.getSpawnTime() - this.preSpawn.getValue()))) {
                if (!this.earlyFeetThread.getValue()) {
                    this.threadHelper.startThread(new BlockPos[0]);
                }
                else if (this.lateBreakThread.getValue()) {
                    this.threadHelper.startThread(true, false, new BlockPos[0]);
                }
            }
            else if (ServerTimeHelper.getClosestEnemy() != null && ServerTimeHelper.isSemiSafe(ServerTimeHelper.getClosestEnemy(), true, this.newVer.getValue()) && canBeFeetPlaced(ServerTimeHelper.getClosestEnemy(), true, this.newVer.getValue()) && this.earlyFeetThread.getValue() && Thunderhack.servtickManager.valid(Thunderhack.servtickManager.getTickTimeAdjusted(), 0, this.maxEarlyThread.getValue())) {
                this.threadHelper.startThread(false, true, new BlockPos[0]);
            }
        }
    }
    
    @SubscribeEvent
    public void onKeyBoard(final KeyboardEvent event) {
        if (event.getEventState() && event.getKey() == this.switchBind.getValue().getKey()) {
            if (this.useAsOffhand.getValue()) {
                this.switching = false;
            }
            else if (this.autoSwitch.getValue() == AutoSwitch.Bind) {
                this.switching = !this.switching;
                if (this.switchMessage.getValue()) {
                    Command.sendMessage(this.switching ? (TextFormatting.GREEN + "Switch on") : (TextFormatting.RED + "Switch off"));
                }
            }
        }
    }
    
    @SubscribeEvent
    public void nigga(final EventSync event) {
        if (!this.multiThread.getValue() && this.motionCalc.getValue() && (Thunderhack.positionManager.getX() != AutoCrystal.mc.player.posX || Thunderhack.positionManager.getY() != AutoCrystal.mc.player.posY || Thunderhack.positionManager.getZ() != AutoCrystal.mc.player.posZ)) {
            final CalculationMotion calc = new CalculationMotion(this, AutoCrystal.mc.world.loadedEntityList, AutoCrystal.mc.world.playerEntities);
            this.threadHelper.start(calc, false);
        }
        else if (this.motionThread.getValue()) {
            this.threadHelper.startThread(new BlockPos[0]);
        }
        final AbstractCalculation<?> current = this.threadHelper.getCurrentCalc();
        if (current != null && !current.isFinished() && this.rotate.getValue() != ACRotate.None && this.rotationThread.getValue() == RotationThread.Wait) {
            synchronized (this) {
                try {
                    this.wait(this.timeOut.getValue());
                }
                catch (InterruptedException e) {
                    Command.sendMessage("Minecraft Main-Thread interrupted!");
                    Thread.currentThread().interrupt();
                }
            }
        }
        final RotationFunction rotation = this.rotation;
        if (rotation != null) {
            this.isSpoofing = true;
            final float[] rotations = rotation.apply(AutoCrystal.mc.player.posX, AutoCrystal.mc.player.posY, AutoCrystal.mc.player.posZ, event.getYaw(), event.getPitch());
            if (this.rotateMode.getValue() == RotateMode.Smooth) {
                final float yaw = this.yawMouseFilter.smooth(rotations[0] + MathUtil.random(-1.0f, 5.0f), (float)this.smoothSpeed.getValue());
                final float pitch = this.pitchMouseFilter.smooth(rotations[1] + MathUtil.random(-1.2f, 3.5f), (float)this.smoothSpeed.getValue());
                AutoCrystal.mc.player.rotationYaw = yaw;
                AutoCrystal.mc.player.rotationPitch = pitch;
            }
            else {
                AutoCrystal.mc.player.rotationYaw = rotations[0];
                AutoCrystal.mc.player.rotationPitch = rotations[1];
            }
        }
        else if (this.rayTraceBypass.getValue() && this.forceBypass.getValue()) {
            final BlockPos bypassPos = this.getBypassPos();
            if (bypassPos != null) {
                final float[] rotations2 = RotationUtil.getRotationsToTopMiddleUp(bypassPos);
                final float pitch = (rotations2[1] == 0.0f && this.rbYaw.getValue() != 0.0f) ? 0.0f : ((rotations2[1] < 0.0f) ? (rotations2[1] + this.rbPitch.getValue()) : (rotations2[1] - this.rbPitch.getValue()));
                AutoCrystal.mc.player.rotationYaw = (rotations2[0] + this.rbYaw.getValue()) % 360.0f;
                AutoCrystal.mc.player.rotationPitch = pitch;
            }
        }
    }
    
    @SubscribeEvent
    public void onPostMotion(final EventPostSync e) {
        this.motionID.incrementAndGet();
        synchronized (this.post) {
            this.runPost();
        }
        this.isSpoofing = false;
    }
    
    @SubscribeEvent
    public void onMotion(final NoMotionUpdateEvent event) {
        if (this.multiThread.getValue() && !this.isSpoofing && this.rotate.getValue() != ACRotate.None && this.rotationThread.getValue() == RotationThread.Cancel) {
            this.forward = -this.forward;
            final float yaw = Thunderhack.rotationManager.getServerYaw() + this.forward;
            final float pitch = Thunderhack.rotationManager.getServerPitch() + this.forward;
            this.rotationCanceller.onPacketNigger9(new CPacketPlayer.Rotation(yaw, pitch, Thunderhack.positionManager.isOnGround()));
        }
        else {
            this.rotationCanceller.reset();
        }
    }
    
    protected void onEvent22(final SPacketEntity packet) {
        if (!this.shouldCalc22()) {
            return;
        }
        EntityPlayer p = null;
        if (AutoCrystal.mc.world.getEntityByID(((ISPacketEntity)packet).getEntityId()) instanceof EntityPlayer) {
            p = (EntityPlayer)AutoCrystal.mc.world.getEntityByID(((ISPacketEntity)packet).getEntityId());
        }
        if (p == null) {
            return;
        }
        final double x = (p.serverPosX + packet.getX()) / 4096.0;
        final double y = (p.serverPosY + packet.getY()) / 4096.0;
        final double z = (p.serverPosZ + packet.getZ()) / 4096.0;
        this.onEvent22(p, x, y, z);
    }
    
    protected void onEvent22(final EntityPlayer player, final double x, final double y, final double z) {
        final Entity entity = (Entity)com.mrzak34.thunderhack.util.RotationUtil.getRotationPlayer();
        if (entity != null && entity.getDistanceSq(x, y, z) < MathUtil.square(this.targetRange.getValue()) && !Thunderhack.friendManager.isFriend(player)) {
            List<EntityPlayer> enemies;
            EntityPlayer target;
            Scheduler.getInstance().scheduleAsynchronously(() -> {
                if (AutoCrystal.mc.world != null) {
                    enemies = Collections.emptyList();
                    target = this.getTTRG(AutoCrystal.mc.world.playerEntities, enemies, this.targetRange.getValue());
                    if (target == null || target.equals((Object)player)) {
                        this.threadHelper.startThread(new BlockPos[0]);
                    }
                }
            });
        }
    }
    
    public EntityPlayer getTTRG(final List<EntityPlayer> players, final List<EntityPlayer> enemies, final Float maxRange) {
        switch (this.targetMode.getValue()) {
            case Fov: {
                final EntityPlayer enemy = getByFov(enemies, maxRange);
                if (enemy == null) {
                    return getByFov(players, maxRange);
                }
                return enemy;
            }
            case Angle: {
                final EntityPlayer enemy = getByAngle(enemies, maxRange);
                return (enemy == null) ? getByAngle(players, maxRange) : enemy;
            }
            case Damage: {
                return null;
            }
            case Closest: {
                return ServerTimeHelper.getClosestEnemy(AutoCrystal.mc.player.posX, AutoCrystal.mc.player.posY, AutoCrystal.mc.player.posZ, maxRange, enemies, players);
            }
            default: {
                return null;
            }
        }
    }
    
    protected boolean shouldCalc22() {
        return this.multiThread.getValue() && this.entityThread.getValue() && (this.rotate.getValue() == ACRotate.None || this.rotationThread.getValue() != RotationThread.Predict);
    }
    
    protected EntityPlayer getEntity22(final int id) {
        final List<Entity> entities = (List<Entity>)AutoCrystal.mc.world.loadedEntityList;
        if (entities == null) {
            return null;
        }
        Entity entity = null;
        for (final Entity e : entities) {
            if (e != null && e.getEntityId() == id) {
                entity = e;
                break;
            }
        }
        if (entity instanceof EntityPlayer) {
            return (EntityPlayer)entity;
        }
        return null;
    }
    
    private void onEvent33(final PacketEvent.Receive event) {
        final World world = (World)AutoCrystal.mc.world;
        if (AutoCrystal.mc.player == null || world == null || this.basePlaceOnly.getValue() || ((SPacketSpawnObject)event.getPacket()).getType() != 51 || AutoCrystal.mc.world == null || (!this.spectator.getValue() && AutoCrystal.mc.player.isSpectator()) || (this.stopWhenEating.getValue() && this.isEating()) || (this.stopWhenMining.getValue() && this.isMining()) || ((ISPacketSpawnObject)event.getPacket()).isAttacked()) {
            return;
        }
        final SPacketSpawnObject packet = (SPacketSpawnObject)event.getPacket();
        final double x = packet.getX();
        final double y = packet.getY();
        final double z = packet.getZ();
        final EntityEnderCrystal entity = new EntityEnderCrystal(world, x, y, z);
        if (this.simulatePlace.getValue() != 0) {
            final SPacketSpawnObject sPacketSpawnObject;
            Entity e;
            event.addPostEvent(() -> {
                if (AutoCrystal.mc.world == null) {
                    return;
                }
                else {
                    e = AutoCrystal.mc.world.getEntityByID(sPacketSpawnObject.getEntityID());
                    if (e instanceof EntityEnderCrystal) {
                        this.crystalRender.onSpawn((EntityEnderCrystal)e);
                    }
                    return;
                }
            });
        }
        if (!this.instant.getValue() || !this.breakTimer.passed(this.breakDelay.getValue())) {
            return;
        }
        final BlockPos pos = new BlockPos(x, y, z);
        final CrystalTimeStamp stamp = this.placed.get(pos);
        entity.setShowBottom(false);
        entity.setEntityId(packet.getEntityID());
        entity.setUniqueId(packet.getUniqueId());
        boolean attacked = false;
        if ((!this.alwaysCalc.getValue() || (pos.equals((Object)this.bombPos) && this.alwaysBomb.getValue())) && stamp != null && stamp.isValid() && (stamp.getDamage() > this.slowBreakDamage.getValue() || stamp.isShield() || this.breakTimer.passed(this.slowBreakDelay.getValue()) || pos.down().equals((Object)this.antiTotemHelper.getTargetPos()))) {
            if (pos.equals((Object)this.bombPos)) {
                this.bombPos = null;
            }
            final float damage = this.checkPos((Entity)entity);
            if (damage <= -1000.0f) {
                final MutableWrapper<Boolean> a = new MutableWrapper<Boolean>(false);
                this.rotation = this.rotationHelper.forBreaking((Entity)entity, a);
                final SPacketSpawnObject sPacketSpawnObject2;
                Entity e2;
                final MutableWrapper<Boolean> mutableWrapper;
                event.addPostEvent(() -> {
                    if (AutoCrystal.mc.world != null) {
                        e2 = AutoCrystal.mc.world.getEntityByID(sPacketSpawnObject2.getEntityID());
                        if (e2 != null) {
                            this.post.add(this.rotationHelper.post(e2, mutableWrapper));
                            this.rotation = this.rotationHelper.forBreaking(e2, mutableWrapper);
                            this.setCrystal(e2);
                        }
                    }
                });
                return;
            }
            if (damage < 0.0f) {
                return;
            }
            if (damage > this.shieldSelfDamage.getValue() && stamp.isShield()) {
                return;
            }
            this.attack(packet, event, entity, stamp.getDamage() <= this.slowBreakDamage.getValue());
            attacked = true;
        }
        else if (this.asyncCalc.getValue() || this.alwaysCalc.getValue()) {
            final List<EntityPlayer> players = (List<EntityPlayer>)AutoCrystal.mc.world.playerEntities;
            if (players == null) {
                return;
            }
            final float self = this.checkPos((Entity)entity);
            if (self < 0.0f) {
                return;
            }
            boolean slow = true;
            boolean attack = false;
            for (final EntityPlayer player : players) {
                if (player != null && !EntityUtil.isDead((Entity)player)) {
                    if (player.getDistanceSq(x, y, z) > 144.0) {
                        continue;
                    }
                    if (Thunderhack.friendManager.isFriend(player) && (!this.isSuicideModule() || !player.equals((Object)AutoCrystal.mc.player))) {
                        if (this.shouldCalcFuckinBitch(AntiFriendPop.Break) && this.damageHelper.getDamage((Entity)entity, (EntityLivingBase)player) > EntityUtil.getHealth((Entity)player) - 0.5f) {
                            attack = false;
                            break;
                        }
                        continue;
                    }
                    else {
                        final float dmg = this.damageHelper.getDamage((Entity)entity, (EntityLivingBase)player);
                        if ((dmg <= self && (!this.suicide.getValue() || dmg < this.minDamage.getValue())) || dmg <= this.minBreakDamage.getValue() || (dmg <= this.slowBreakDamage.getValue() && !this.shouldDanger() && !this.breakTimer.passed(this.slowBreakDelay.getValue()))) {
                            continue;
                        }
                        slow = (slow && dmg <= this.slowBreakDamage.getValue());
                        attack = true;
                    }
                }
            }
            if (attack) {
                this.attack(packet, event, entity, (stamp == null || !stamp.isShield()) && slow);
                attacked = true;
            }
            else if (stamp != null && stamp.isShield() && self >= 0.0f && self <= this.shieldSelfDamage.getValue()) {
                this.attack(packet, event, entity, false);
                attacked = true;
            }
        }
        if (this.spawnThread.getValue() && (!this.spawnThreadWhenAttacked.getValue() || attacked)) {
            this.threadHelper.schedulePacket(event);
        }
    }
    
    private void attack(final SPacketSpawnObject packet, final PacketEvent.Receive event, final EntityEnderCrystal entityIn, final boolean slow) {
        HelperInstantAttack.attack(this, packet, event, entityIn, slow);
    }
    
    private float checkPos(final Entity entity) {
        final BreakValidity validity = HelperUtil.isValid(this, entity, true);
        switch (validity) {
            case INVALID: {
                return -1.0f;
            }
            case ROTATIONS: {
                final float damage = this.getSelfDamage(entity);
                if (damage < 0.0f) {
                    return damage;
                }
                return -1000.0f - damage;
            }
            default: {
                return this.getSelfDamage(entity);
            }
        }
    }
    
    private float getSelfDamage(final Entity entity) {
        final float damage = this.damageHelper.getDamage(entity);
        return (damage > this.maxSelfBreak.getValue() || (damage > EntityUtil.getHealth((Entity)AutoCrystal.mc.player) - 1.0f && !this.suicide.getValue())) ? -1.0f : damage;
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.render.getValue() && this.box.getValue() && this.fade.getValue()) {
            for (final Map.Entry<BlockPos, Long> set : this.fadeList.entrySet()) {
                if (this.getRenderPos() == set.getKey()) {
                    continue;
                }
                final Color boxColor = this.boxColor.getValue().getColorObject();
                final Color outlineColor = this.outLine.getValue().getColorObject();
                final float maxBoxAlpha = (float)boxColor.getAlpha();
                final float maxOutlineAlpha = (float)outlineColor.getAlpha();
                final float alphaBoxAmount = maxBoxAlpha / this.fadeTime.getValue();
                final float alphaOutlineAmount = maxOutlineAlpha / this.fadeTime.getValue();
                final int fadeBoxAlpha = MathHelper.clamp((int)(alphaBoxAmount * (set.getValue() + this.fadeTime.getValue() - System.currentTimeMillis())), 0, (int)maxBoxAlpha);
                final int fadeOutlineAlpha = MathHelper.clamp((int)(alphaOutlineAmount * (set.getValue() + this.fadeTime.getValue() - System.currentTimeMillis())), 0, (int)maxOutlineAlpha);
                if (!this.box.getValue()) {
                    continue;
                }
                BlockRenderUtil.prepareGL();
                TessellatorUtil.drawBox(interpolatePos(set.getKey(), 1.0f), new Color(boxColor.getRed(), boxColor.getGreen(), boxColor.getBlue(), fadeBoxAlpha));
                BlockRenderUtil.releaseGL();
                BlockRenderUtil.prepareGL();
                TessellatorUtil.drawBoundingBox(interpolatePos(set.getKey(), 1.0f), 1.5, new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue(), fadeOutlineAlpha));
                BlockRenderUtil.releaseGL();
            }
        }
        final BlockPos pos;
        if (this.render.getValue() && (pos = this.getRenderPos()) != null) {
            if (!this.fade.getValue() && this.box.getValue()) {
                BlockRenderUtil.prepareGL();
                TessellatorUtil.drawBox(interpolatePos(pos, 1.0f), this.boxColor.getValue().getColorObject());
                BlockRenderUtil.releaseGL();
                BlockRenderUtil.prepareGL();
                TessellatorUtil.drawBoundingBox(interpolatePos(pos, 1.0f), 1.5, this.outLine.getValue().getColorObject());
                BlockRenderUtil.releaseGL();
            }
            if (this.renderDamage.getValue() != RenderDamagePos.None) {
                this.renderDamage(pos);
            }
            if (this.fade.getValue()) {
                this.fadeList.put(pos, System.currentTimeMillis());
            }
        }
        this.fadeList.entrySet().removeIf(e -> e.getValue() + this.fadeTime.getValue() < System.currentTimeMillis());
        if (this.renderExtrapolation.getValue()) {
            for (final EntityPlayer player : AutoCrystal.mc.world.playerEntities) {
                final MotionTracker tracker;
                if (player != null && !EntityUtil.isDead((Entity)player) && RenderUtil.getEntity().getDistanceSq((Entity)player) <= 200.0 && RotationUtil.inFov((Entity)player) && !player.equals((Object)AutoCrystal.mc.player) && (tracker = this.extrapolationHelper.getTrackerFromEntity((Entity)player)) != null) {
                    if (!tracker.active) {
                        continue;
                    }
                    final Vec3d interpolation = EntityUtil.interpolateEntity((Entity)player, AutoCrystal.mc.getRenderPartialTicks());
                    final double x = interpolation.x - Trajectories.getRenderPosX();
                    final double y = interpolation.y - Trajectories.getRenderPosY();
                    final double z = interpolation.z - Trajectories.getRenderPosZ();
                    final double tX = tracker.posX - Trajectories.getRenderPosX();
                    final double tY = tracker.posY - Trajectories.getRenderPosY();
                    final double tZ = tracker.posZ - Trajectories.getRenderPosZ();
                    GlStateManager.enableAlpha();
                    GlStateManager.enableBlend();
                    GlStateManager.pushMatrix();
                    GlStateManager.loadIdentity();
                    if (Thunderhack.friendManager.isFriend(player)) {
                        GL11.glColor4f(0.33333334f, 0.78431374f, 0.78431374f, 0.55f);
                    }
                    else {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    }
                    final boolean viewBobbing = AutoCrystal.mc.gameSettings.viewBobbing;
                    AutoCrystal.mc.gameSettings.viewBobbing = false;
                    ((IEntityRenderer)AutoCrystal.mc.entityRenderer).orientCam(event.getPartialTicks());
                    AutoCrystal.mc.gameSettings.viewBobbing = viewBobbing;
                    GL11.glLineWidth(1.5f);
                    GL11.glBegin(1);
                    GL11.glVertex3d(tX, tY, tZ);
                    GL11.glVertex3d(x, y, z);
                    GL11.glEnd();
                    GlStateManager.popMatrix();
                    GlStateManager.disableAlpha();
                    GlStateManager.disableBlend();
                }
            }
        }
    }
    
    private void renderDamage(final BlockPos pos) {
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        final String text = this.damage;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        final double x = pos.getX() + 0.5;
        final double y = pos.getY() + ((this.renderDamage.getValue() == RenderDamagePos.OnTop) ? 1.35 : 0.5);
        final double z = pos.getZ() + 0.5;
        final float scale = 0.016666668f * ((this.renderMode.getValue() == RenderDamage.Indicator) ? 0.95f : 1.3f);
        GlStateManager.translate(x - Trajectories.getRenderPosX(), y - Trajectories.getRenderPosY(), z - Trajectories.getRenderPosZ());
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-AutoCrystal.mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(AutoCrystal.mc.player.rotationPitch, (AutoCrystal.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        final int distance = (int)AutoCrystal.mc.player.getDistance(x, y, z);
        float scaleD = distance / 2.0f / 3.0f;
        if (scaleD < 1.0f) {
            scaleD = 1.0f;
        }
        GlStateManager.scale(scaleD, scaleD, scaleD);
        GlStateManager.translate(-(FontRender.getStringWidth6(text) / 2.0), 0.0, 0.0);
        FontRender.drawString6(text, 0.0f, 0.0f, -1, false);
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @SubscribeEvent
    public void onRenderEntity(final PostRenderEntitiesEvent event) {
        if (event.getPass() == 0) {
            this.crystalRender.render(event.getPartialTicks());
        }
    }
    
    static {
        POSITION_HISTORY = new PositionHistoryHelper();
        EXECUTOR = ThreadUtil.newDaemonScheduledExecutor("AutoCrystal");
        ATOMIC_STARTED = new AtomicBoolean();
        AutoCrystal.timercheckerfg = new Timer();
        AutoCrystal.timercheckerwfg = new Timer();
        AutoCrystal.psdead = true;
        MinecraftForge.EVENT_BUS.register((Object)AutoCrystal.POSITION_HISTORY);
    }
    
    public enum settingtypeEn
    {
        Noob, 
        Pro, 
        Hacker;
    }
    
    public enum pages
    {
        Place, 
        Break, 
        Rotations, 
        Misc, 
        FacePlace, 
        SwitchNSwing, 
        Render, 
        Predict, 
        Dev, 
        SetDead, 
        Obsidian, 
        Liquid, 
        AntiTotem, 
        DamageSync, 
        Extrapolation, 
        Efficiency, 
        MultiThreading;
    }
    
    public enum ACRotate
    {
        None, 
        All, 
        Break, 
        Place;
    }
    
    public enum AntiFriendPop
    {
        All, 
        Break, 
        Place, 
        None;
    }
    
    public enum AntiWeakness
    {
        None, 
        Switch;
    }
    
    public enum Attack2
    {
        Always, 
        Crystal, 
        Calc;
    }
    
    public enum BlockExtrapolationMode
    {
        Extrapolated, 
        Pessimistic, 
        Optimistic;
    }
    
    public enum BreakValidity
    {
        INVALID, 
        ROTATIONS, 
        VALID;
    }
    
    public enum ExtrapolationType
    {
        None, 
        Place, 
        Break, 
        Block;
    }
    
    public enum PreCalc
    {
        None, 
        Target, 
        Damage;
    }
    
    public enum RenderDamage
    {
        Normal, 
        Indicator;
    }
    
    public enum RenderDamagePos
    {
        None, 
        Inside, 
        OnTop;
    }
    
    public enum RotateMode
    {
        Normal, 
        Smooth;
    }
    
    public enum RotationThread
    {
        Predict, 
        Cancel, 
        Wait;
    }
    
    public enum SwingTime
    {
        None, 
        Pre, 
        Post;
    }
    
    public enum SwingType
    {
        None, 
        MainHand, 
        OffHand;
    }
    
    public enum Target
    {
        Closest, 
        Damage, 
        Angle, 
        Fov;
    }
    
    public enum SmartRange
    {
        None, 
        Normal, 
        All, 
        Extrapolated;
    }
    
    public enum AutoSwitch
    {
        None, 
        Bind, 
        Always;
    }
    
    public enum CooldownBypass2
    {
        None, 
        Swap, 
        Pick, 
        Slot;
    }
    
    public enum RayTraceMode
    {
        Fast, 
        Resign, 
        Force, 
        Smart;
    }
    
    public enum PlaceSwing
    {
        Always, 
        Never, 
        Once;
    }
    
    public enum Rotate
    {
        None, 
        Normal, 
        Packet;
    }
}
