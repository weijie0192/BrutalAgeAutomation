package events.register;

import events.common.Event;

import java.util.HashMap;

public class GetGiftEvents {

    public static void register(HashMap<String, Event> _map) {


        Event.builder(_map, "buy_meat")
                .setDelay(1)
                .setListener(((event, game) -> {
                    game.dispatch.delay(1);
                    game.dispatch(Event.builder().setLoc(285, 85).setDelay(1.5));
                    game.dispatch(Event.builder().setLoc(485, 711).setDelay(1.5));
                    game.dispatch(Event.builder().setLoc(485, 711).setDelay(1.5));
                    game.dispatch("top_left");
                    return Event.SUCCESS;
                }));


        Event.builder(_map, "help")
                .setDelay(1)
                .setLoc(675, 742);

        Event.builder(_map, "get_all_mail")
                .setTargetName("side_1:btn_mail")
                .setDelay(1.5)
                .setLoc(673, 1009)
                .setListener(((event, game) -> {
                    game.dispatch( Event.builder()
                            .setDelay(1.5)
                            .setLoc(376, 430));

                    game.dispatch(Event.builder()
                            .setDelay(1.5)
                            .setLoc(351, 1184));

                    if(game.log.btnName.contains(("edit"))){
                        game.dispatch("top_left");
                    }else{
                       game.dispatch( Event.builder()
                               .setTargetName("board:btn_collect")
                               .setDelay(2.5)
                               .setLoc(358, 907));
                    }
                    game.dispatch("top_left");
                    game.dispatch("top_left");
                    return Event.SUCCESS;
                }));


        Event.builder(_map, "collect_workshop_gift")
                .setDelay(1.5)
                .setLoc(578, 256)
                .setListener((event, game) -> {
                    while(game.log.btnName.contains("btn_collect")){
                        game.dispatch(Event.builder().setDelay(1.5).setLoc(579, 256));
                    }

                    if (game.log.btnName.contains("btn_process")) {
                        game.dispatch("top_left");
                    }
                    return Event.SUCCESS;
                })
                .setChain(_map.get("top_left"));


        Event.builder(_map, "get_workshop_gift")
                .setDelay(1.5)
                .setListener(((event, game) -> {
                    game.dispatch.delay(2);
                    game.dispatch(Event.builder().setTargetName("click question 1").setLoc(406, 615).setDelay(1.5));
                    game.dispatch("collect_workshop_gift");

                    if(!game.store.metadata.getFeatureToggler().getGlobalFeatures().get("No Clan")){
                        game.dispatch(Event.builder()
                                .setLoc(391, 774)
                                .setDelay(1.5));
                        game.dispatch("collect_workshop_gift");
                    }

                    game.dispatch("top_left");
                    return Event.SUCCESS;

                }));


        Event.builder(_map, "get_quest_gift_single")
                .setDelay(1)
                .setLoc(28, 1103);


        Event.builder(_map, "get_quest_gift")
                .setTargetName("reward:btn_reward")
                .setDelay(1.25)
                .setLoc(28, 1103)
                .setListener(
                        (event, game) -> {
                            int redo = 15;
                            Event tempEvent = Event.builder().setDelay(0.25).setLoc(28,1103);
                            while(!game.log.btnName.equalsIgnoreCase("quest:btn_task") && redo-- > 0){
                                game.dispatch(tempEvent);
                            }

                            game.dispatch("top_left");
                            return event;
                        }
                );

        Event.builder(_map, "use_all")
                .setTargetName("reward:btn_reward")
                .setDelay(1.25)
                .setLoc(453, 581, 550, 581, 300)
                .setChain(
                        Event.builder()
                                .setDelay(1.25)
                                .setLoc(360, 700)
                );

        Event.builder(_map, "use_resource")
                .setTargetName("reward:btn_reward")
                .setDelay(1.25)
                .setLoc(600, 300)
                .setListener(
                        (event, game) -> {
                            if (game.log.btnName.contains("btn_use")) {
                                game.dispatch("use_all");
                                return null;
                            }
                            else if(game.log.btnName.contains("main:ui_mb")){
                                game.dispatch(Event.builder().setLoc(370, 636).setDelay(1));
                            }
                            return event;
                        }
                );

        Event.builder(_map, "use_all_resource")
                .setDelay(1.25)
                .setListener(
                        (event, game) -> {
                            while (game.dispatch("use_resource")) ;
                            game.dispatch(Event.builder().setLoc(600, 300, 140, 300, 500).setDelay(1.25));
                            while (game.dispatch("use_resource")) ;
                            game.dispatch("top_left");
                            return null;
                        }
                );

        Event.builder(_map, "use_hammer")
                .setLoc(37, 330)
                .setDelay(2)
                .setChain(
                        Event.builder()
                                .setDelay(2)
                                .setLoc(314, 905),
                        Event.builder()
                                .setDelay(2)
                                .setLoc(100, 360)
                                .setListener(((event, game) -> {
                                    if (game.log.btnName.contains("btn_close")) {
                                        return event;
                                    }
                                    return null;
                                }))
                );
    }
}
