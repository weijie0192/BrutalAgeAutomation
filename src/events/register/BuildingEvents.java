package events.register;

import events.EventMap;
import events.common.Event;
import game.GameInstance;
import store.Account;
import util.Logger;

import java.util.*;

public class BuildingEvents {

    public static ArrayList<String> AllBuildings = new ArrayList<>();


    public static void register(HashMap<String, Event> _map) {

        Event.builder(_map, "test_upgrade_building")
                .setDelay(1)
                .setListener((event, game) -> {
                    for (int i = 634; i <= 1026; i += 78) {
                        game.dispatch(Event.builder().setLoc(631, i).setDelay(1));
                        if (game.log.btnName.contains("research") || game.log.btnName.contains("add")) {
                            return null;
                        }
                    }
                    return event;
                });

        Event.builder(_map, "upgrade_building")
                .setLoc(527, 1165)
                .setDelay(1)
                .setListener((event, game) -> game.log.btnName.contains("btn_upgrade") ? null : event);
        Event.builder(_map, "test_upgrade_secondary")
                .setLoc(359, 890, 359, 957, 200)
                .setDelay(1)
                .setListener(((event, game) -> {
                    if (game.log.btnName.toLowerCase().contains("buy") || game.log.btnName.toLowerCase().contains("use")) {
                        return event;
                    }
                    return null;
                }));
        Event.builder(_map, "confirm_stronghold_6")
                .setLoc(491, 634)
                .setDelay(1);
        Event.builder(_map, "upgrade_building_buy")
                .setLoc(200, 1170)
                .setDelay(1)
                .setListener((event, game) -> game.log.btnName.contains("btn_buy") ||
                        game.log.btnName.contains("btn_buildnow") ? null : event);

        Event.builder(_map, "train")
                .setLoc(520, 1141)
                .setDelay(1.5)
                .setListener(((event, game) -> {
                    if (!game.log.btnName.contains("btn_train")) {
                        game.dispatch("top_left");
                    }
                    return null;
                }));

        registerBuilding(_map);

    }


    private static void registerBuilding(HashMap<String, Event> _map) {

        Event.builder(_map, "tap_building")
                .setLoc(350, 630)
                .setDelay(1.5);

        Event.builder(_map, "fire")
                .isBuilding()
                .setLoc(-574, -349, 432, 434);

        Event.builder(_map, "monster_access")
                .isBuilding()
                .setLoc(-574, -349, 432, 434)
                .setListener(((event, game) -> {
                    game.dispatch.delay(1.5);
                    game.dispatch(Event.builder().setLoc(65, 642).setDelay(2));
                    Event hitMonster = Event.builder().setLoc(340, 681).setDelay(1);
                    int redo = 12;
                    do {
                        game.dispatch(hitMonster);
                    } while (game.log.btnName.contains("monster_stage:hitzone") && redo-- > 0);

                    if (game.log.btnName.contains("btn_confirm")) {
                        game.dispatch("top_left");
                    }
                    game.dispatch("top_left");
                    return null;
                }));

        Event.builder(_map, "stronghold")
                .setTargetName("loc_1")
                .isBuilding(true)
                .setLoc(-1591, -306, 400, 513);

        Event.builder(_map, "portal")
                .setTargetName("loc_2")
                .isBuilding(true)
                .setLoc(-315, -649, 500, 505);


        Event.builder(_map, "war_camp")
                .setTargetName("loc_3")
                .isBuilding(true)
                .setLoc(-1227, -1185, 386, 474);

        Event.builder(_map, "healing_spring")
                .setTargetName("loc_4")
                .isBuilding(true)
                .setLoc(-1289, -895, 480, 483);

        Event.builder(_map, "healing_spring_access")
                .setTargetName("loc_4")
                .isBuilding()
                .isAccess()
                .setLoc(-1289, -895, 245, 480)
                .setChain(
                        Event.builder()
                                .setLoc(588, 1181)
                                .setDelay(1.5),
                        Event.builder()
                                .setLoc(356, 1185)
                                .setDelay(1.5),
                        _map.get("top_left")
                );
        ;

        Event.builder(_map, "research")
                .setTargetName("loc_5")
                .isBuilding(true)
                .setLoc(-978, -717, 500, 480);


        Event.builder(_map, "warehouse")
                .setTargetName("loc_11")
                .isBuilding(true)
                .setLoc(-2296, -1489, 500, 480);


        Event.builder(_map, "tower")
                .setTargetName("loc_12")
                .isBuilding(true)
                .setLoc(-2328, -300, 480, 480);

        Event.builder(_map, "golden_tree")
                .setTargetName("loc_13")
                .isBuilding(true)
                .setLoc(-2449, -1071, 480, 480);

        Event.builder(_map, "golden_tree_access")
                .setTargetName("loc_13")
                .isBuilding()
                .isAccess()
                .setLoc(-2449, -1071, 230, 480)
                .setChain(
                        Event.builder()
                                .setLoc(190, 630)
                                .setDelay(1.5)
                                .setListener((event, game) -> {
                                            while (game.log.btnName.contains("BODY:btn_rss")) {
                                                if (game.account != null && game.account.getBuildingLvl("stronghold") >= 8 && game.account.isRssLessThan("rock", "wood")) {
                                                    game.dispatch(Event.builder()
                                                            .setLoc(520, 812)
                                                            .setDelay(1.2));
                                                } else {
                                                    game.dispatch(Event.builder()
                                                            .setLoc(190, 630)
                                                            .setDelay(1.2));
                                                }
                                            }
                                            game.dispatch(Event.builder()
                                                    .setLoc(72, 323)
                                                    .setDelay(1.5)
                                            );
                                            game.dispatch("top_left");
                                            return null;
                                        }
                                )
                );

        Event.builder(_map, "workshop")
                .setTargetName("loc_8")
                .isBuilding()
                .setLoc(-2174, -1242)
                .setChain(_map.get("get_workshop_gift"));


        Event.builder(_map, "daily_reward")
                .setTargetName("loc_9")
                .setLoc(-1739, -895)
                .isBuilding()
                .setListener((event, game) -> {
                    game.dispatch.delay(1.0);
                    game.dispatch(
                            Event.builder().setDelay(2)
                                    .setLoc(396, 998));
                    game.dispatch(
                            Event.builder().setDelay(1.5)
                                    .setLoc(84, 247));
                    return null;
                });


        Event.builder(_map, "help_wagon")
                .setTargetName("loc_15")
                .isBuilding(true)
                .setLoc(-545, -1802, 480, 500);

        Event.builder(_map, "warrior")
                .setTargetName("loc_16")
                .isBuilding()
                .setLoc(-991, -1379)
                .setChain(
                        _map.get("tap_building"),
                        _map.get("train")
                );
        Event.builder(_map, "rider")
                .setTargetName("loc_17")
                .isBuilding()
                .setLoc(-1258, -1474).setChain(
                _map.get("tap_building"),
                _map.get("train")
        );

        Event.builder(_map, "shaman")
                .setTargetName("loc_18")
                .isBuilding()
                .setLoc(-1524, -1351).setChain(
                _map.get("tap_building"),
                _map.get("train")
        );

        Event.builder(_map, "well1")
                .setTargetName("loc_19")
                .isBuilding(true)
                .setLoc(-1131, -1870, 334, 500);

        Event.builder(_map, "well2")
                .setTargetName("loc_20")
                .isBuilding(true)
                .setLoc(-1373, -1863, 334, 500);

        Event.builder(_map, "well3")
                .setTargetName("loc_21")
                .isBuilding(true)
                .setLoc(-916, -2068, 334, 500);

        Event.builder(_map, "well4")
                .setTargetName("loc_22")
                .isBuilding(true)
                .setLoc(-1166, -2070, 334, 500);

        Event.builder(_map, "well5")
                .setTargetName("loc_23")
                .isBuilding(true)
                .setLoc(-1446, -2041, 334, 500);

        Event.builder(_map, "warhub1")
                .setTargetName("loc_24")
                .isBuilding(true)
                .setLoc(-1616, -1767, 360, 510);

        Event.builder(_map, "warhub2")
                .isBuilding(true)
                .setTargetName("loc_25")
                .setLoc(-1821, -1666, 360, 510);

        Event.builder(_map, "warhub3")
                .isBuilding(true)
                .setTargetName("loc_26")
                .setLoc(-1717, -1944, 360, 510);

        Event.builder(_map, "warhub4")
                .isBuilding(true)
                .setTargetName("loc_27")
                .setLoc(-1910, -1853, 360, 510);

        Event.builder(_map, "warhub5")
                .isBuilding(true)
                .setTargetName("loc_28")
                .setLoc(-2065, -1735, 360, 510);

        Event.builder(_map, "squirrel")
                .setTargetName("loc_28")
                .isBuilding()
                .setLoc(-1978, -1322)
                .setChain(
                        Event.builder().setLoc(350, 630).setDelay(1),
                        Event.builder().setLoc(156, 768).setDelay(1.25),
                        Event.builder().setLoc(156, 768).setDelay(1.5),
                        Event.builder().setLoc(156, 768).setDelay(1.5)
                );
        Event.builder(_map, "flag")
                .setTargetName("building:flag")
                .isBuilding()
                .setLoc(-871, -998)
                .setChain(_map.get("tap_building"));

        for (Map.Entry<String, Event> entry : EventMap.getMap().entrySet()) {
            if (entry.getValue().isUpgrade) {
                AllBuildings.add(entry.getKey());
            }
        }
    }

}
