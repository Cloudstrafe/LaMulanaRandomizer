package lmr.randomizer.random;

import javafx.util.Pair;
import lmr.randomizer.ItemConstants;
import lmr.randomizer.DataFromFile;
import lmr.randomizer.Settings;
import lmr.randomizer.node.CustomItemPlacement;
import lmr.randomizer.node.MoneyChecker;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by thezerothcat on 8/19/2017.
 */
public class ShopItemPriceCountRandomizer {
    private boolean specialAmmoPlaced = false;
    private boolean normalPriceWeightsPlaced = false;

    private boolean subweaponOnly;

    private MoneyChecker moneyChecker;

    private Random random;

//    // 50-70
//    private List<String> PRICE_TIER1 = Arrays.asList("yagomap.exe", "bunemon.exe", "Glove", "Shell Horn",
//            "xmailer.exe", "bunplus.com", "guild.exe", Constants.BUCKLER, "Helmet", "Bronze Mirror", "emusic.exe", "beolamu.exe",
//            "Waterproof Case", "Heatproof Case", "Map");
//
//    // 80-120
//    private List<String> PRICE_TIER2 = Arrays.asList("Key of Eternity", "Birth Seal", "Life Seal", "Death Seal",
//            Constants.KNIFE, Constants.KEY_SWORD, Constants.SHURIKEN, Constants.ROLLING_SHURIKEN, "Scalesphere", "Pepper", "Talisman", "Magatama Jewel",
//            "yagostr.exe", "Mini Doll", "Treasures", "Anchor", "Grapple Claw", "Perfume", Constants.HAND_SCANNER, Constants.HERMES_BOOTS,
//            "bounce.exe", Constants.ANKH_JEWEL, "Woman Statue", "Maternity Statue", Constants.FLARE_GUN, "Serpent Staff", "Holy Grail");
//
//    // 140-160
//    private List<String> PRICE_TIER3 = Arrays.asList("Feather", "Origin Seal", "Fruit of Eden", "Twin Statue",
//            "Eye of Truth", "Diary", "Ice Cape", "Dragon Bone", Constants.CALTROPS, Constants.EARTH_SPEAR, "Pistol", Constants.KATANA,
//            Constants.FAKE_SILVER_SHIELD, Constants.SILVER_SHIELD, "Isis' Pendant", "Bracelet", "Crucifix", "miracle.exe", "torude.exe",
//            "mirai.exe", "mekuri.exe", "reader.exe", "capstar.exe", "Sacred Orb", "Mobile Super X2");
//
//    // 180-220
//    private List<String> PRICE_TIER4 = Arrays.asList("Plane Model", "Philosopher's Ocarina", "Dimensional Key",
//            "mantra.exe", "Djed Pillar", "Cog of the Soul", "Crystal Skull", "Mulana Talisman", "Vessel", "Pochette Key",
//            "Ring", Constants.CHAIN_WHIP, Constants.AXE, Constants.CHAKRAM,  Constants.BOMB, "Book of the Dead", Constants.ANGEL_SHIELD, "Lamp of Time",
//            "move.exe", "randc.exe");
//
//    // 250-350
//    private List<String> PRICE_TIER5 = Arrays.asList("Fairy Clothes", "Scriptures", "Gauntlet", "deathv.exe",
//            "Provocative Bathing Suit", "Spaulder", Constants.FLAIL_WHIP, "lamulana.exe");

    // 40-60
    private List<String> PRICE_TIER1 = Arrays.asList("yagomap.exe", "bunemon.exe", "Glove", "Shell Horn",
            "xmailer.exe", "bunplus.com", "guild.exe", ItemConstants.BUCKLER, "Helmet", "Bronze Mirror", "emusic.exe", "beolamu.exe",
            "Waterproof Case", "Heatproof Case", "Map");

    // 70-110
    private List<String> PRICE_TIER2 = Arrays.asList("Key of Eternity", "Birth Seal", "Life Seal", "Death Seal",
            ItemConstants.KNIFE, ItemConstants.KEY_SWORD, ItemConstants.SHURIKEN, ItemConstants.ROLLING_SHURIKEN, "Scalesphere", "Pepper", "Talisman", "Magatama Jewel",
            "yagostr.exe", "Mini Doll", "Treasures", "Anchor", "Grapple Claw", "Perfume", ItemConstants.HAND_SCANNER, ItemConstants.HERMES_BOOTS,
            "bounce.exe", ItemConstants.ANKH_JEWEL, "Woman Statue", "Maternity Statue", ItemConstants.FLARE_GUN, "Serpent Staff", "Holy Grail",
            "mirai.exe", "mekuri.exe", "reader.exe", "capstar.exe", "Dragon Bone", "Diary", "Eye of Truth",
            "Cog of the Soul", "Dimensional Key", ItemConstants.EARTH_SPEAR, "Pochette Key");

    // 130-150
    private List<String> PRICE_TIER3 = Arrays.asList("Feather", "Origin Seal", "Fruit of Eden", "Twin Statue",
            "Ice Cape", ItemConstants.FAKE_SILVER_SHIELD, ItemConstants.SILVER_SHIELD, "Vessel", ItemConstants.WHIP,
            "Isis' Pendant", "Bracelet", "Crucifix", "miracle.exe", "torude.exe", "Mobile Super X2", "Sacred Orb",
            "mantra.exe", "Djed Pillar", "Plane Model", "Philosopher's Ocarina", "Mulana Talisman", "Book of the Dead",
            ItemConstants.CALTROPS, "Pistol", ItemConstants.KATANA, ItemConstants.CHAIN_WHIP, ItemConstants.CHAKRAM,  ItemConstants.BOMB, ItemConstants.AXE, "Ring", "Crystal Skull",
            "Medicine of the Mind (Green)", "Medicine of the Mind (Red)", "Medicine of the Mind (Yellow)");

    // 170-210
    private List<String> PRICE_TIER4 = Arrays.asList(
            "Lamp of Time", ItemConstants.ANGEL_SHIELD,
            "move.exe", "randc.exe", "Fairy Clothes", "Scriptures", "Gauntlet", "deathv.exe",
            "Provocative Bathing Suit", "Spaulder", ItemConstants.FLAIL_WHIP, "lamulana.exe");

    public ShopItemPriceCountRandomizer(boolean subweaponOnly, MoneyChecker moneyChecker, Random random) {
        this.subweaponOnly = subweaponOnly;
        this.moneyChecker = moneyChecker;
        this.random = random;
    }

    public Pair<Short, Short> getItemPriceAndCount(String location, String itemName) {
        Short price = null;
        Short count = null;
        for(CustomItemPlacement customItemPlacement : DataFromFile.getCustomPlacementData().getCustomItemPlacements()) {
            if(customItemPlacement.getShopPrice() != null && customItemPlacement.getLocation().equals(location)) {
                price = customItemPlacement.getShopPrice();
                count = customItemPlacement.getShopCount();
            }
        }
        if(price == null && count == null && !specialAmmoPlaced && !subweaponOnly
                && "Pistol Ammo".equals(itemName) && !"Pistol".equals(Settings.getCurrentStartingWeapon())) {
            // Special case
            if(random.nextInt(10) == 0) {
                specialAmmoPlaced = true;
                return new Pair<>((short)400, (short)6);
            }
        }
        if(price == null) {
            price = getPrice(itemName, location);
        }
        if(count == null) {
            count = getCount(itemName);
        }
        return new Pair<>(price, count);
    }

    private short getPrice(String itemName, String shopInventoryLocation) {
        if(subweaponOnly && itemName.endsWith(" Ammo")) {
            return 0;
        }
        if(itemName.equals(Settings.getCurrentStartingWeapon() + " Ammo")) {
            return 0;
        }
        if("Weights".equals(itemName)) {
            if(!normalPriceWeightsPlaced) {
                normalPriceWeightsPlaced = true;
                return 10;
            }
            int priceRoll = random.nextInt(20);
            if(priceRoll == 0 || priceRoll == 1) {
                return 15;
            }
            if(priceRoll == 2) {
                return 20;
            }
            return 10;
        }
        if("Shuriken Ammo".equals(itemName)) {
            return 10;
        }
        if("Rolling Shuriken Ammo".equals(itemName)) {
            return 10;
        }
        if("Earth Spear Ammo".equals(itemName)) {
            int priceRoll = random.nextInt(5);
            if(priceRoll < 3) {
                return 15;
            }
            return 20;
        }
        if("Flare Gun Ammo".equals(itemName)) {
            int priceRoll = random.nextInt(4);
            if(priceRoll < 2) {
                return 30;
            }
            if(priceRoll == 2) {
                return 35;
            }
            return 40;
        }
        if("Bomb Ammo".equals(itemName)) {
            int priceRoll = random.nextInt(5);
            if(priceRoll < 2) {
                return 45;
            }
            if(priceRoll == 2) {
                return 50;
            }
            return 40;
        }
        if("Chakram Ammo".equals(itemName)) {
            int priceRoll = random.nextInt(2);
            if(priceRoll == 0) {
                return 45;
            }
            return 40;
        }
        if("Caltrops Ammo".equals(itemName)) {
            int priceRoll = random.nextInt(5);
            if(priceRoll < 4) {
                return 30;
            }
            return 35;
        }
        if("Pistol Ammo".equals(itemName)) {
            // It looks like 6 is the count given by the Moonlight shop
            int priceRoll = random.nextInt(4);
            if(priceRoll < 3) {
                return 400;
            }
            return 350;
        }

        if(itemName.contains("Map")) {
            itemName = "Map";
        }
        else if(itemName.contains(ItemConstants.ANKH_JEWEL)) {
            itemName = ItemConstants.ANKH_JEWEL;
        }
        else if(itemName.contains("Sacred Orb")) {
            itemName = "Sacred Orb";
        }

        Integer shopPrice = moneyChecker == null
                ? null
                : moneyChecker.getShopPrice(itemName, shopInventoryLocation.replaceAll(" Item \\d", ""));
        if(shopPrice != null) {
            return (short)Math.max(1, (shopPrice - 5 * random.nextInt(2)));
        }
        if(PRICE_TIER1.contains(itemName)) {
            return (short)(40 + 5 * random.nextInt(5));
        }
        else if(PRICE_TIER2.contains(itemName)) {
            return (short)(70 + 10 * random.nextInt(5));
        }
        else if(PRICE_TIER3.contains(itemName)) {
            return (short)(130 + 5 * random.nextInt(5));
        }
        else if(PRICE_TIER4.contains(itemName)) {
            return (short)(170 + 10 * random.nextInt(5));
        }
        else if("Coin".equals(itemName)) {
            return (short)0;
        }
//        else if(PRICE_TIER5.contains(itemName)) {
//            return (short)(250 + 50 * random.nextInt(3));
//        }
        return (short)(10 + 10 * random.nextInt(25) + 1);
    }

    private short getCount(String item) {
        if("Weights".equals(item)) {
            return 5;
        }
        if("Shuriken Ammo".equals(item)) {
            return subweaponOnly || Settings.getCurrentStartingWeapon().equals(ItemConstants.SHURIKEN) ? (short)150 : 10;
        }
        if("Rolling Shuriken Ammo".equals(item)) {
            return subweaponOnly || Settings.getCurrentStartingWeapon().equals(ItemConstants.ROLLING_SHURIKEN) ? (short)100 : 10;
        }
        if("Earth Spear Ammo".equals(item)) {
            return subweaponOnly || Settings.getCurrentStartingWeapon().equals(ItemConstants.EARTH_SPEAR) ? (short)80 : 10;
        }
        if("Flare Gun Ammo".equals(item)) {
            return subweaponOnly || Settings.getCurrentStartingWeapon().equals(ItemConstants.FLARE_GUN) ? (short)80 : 10;
        }
        if("Bomb Ammo".equals(item)) {
            return subweaponOnly || Settings.getCurrentStartingWeapon().equals(ItemConstants.BOMB) ? (short)30 : 10;
        }
        if("Chakram Ammo".equals(item)) {
            return subweaponOnly || Settings.getCurrentStartingWeapon().equals(ItemConstants.CHAKRAM) ? (short)10 : 2;
        }
        if("Caltrops Ammo".equals(item)) {
            return subweaponOnly || Settings.getCurrentStartingWeapon().equals(ItemConstants.CALTROPS) ? (short)80 : 10;
        }
        if("Pistol Ammo".equals(item)) {
            return subweaponOnly || Settings.getCurrentStartingWeapon().equals("Pistol") ? (short)3 : 1;
        }
        return 1;
    }
}
