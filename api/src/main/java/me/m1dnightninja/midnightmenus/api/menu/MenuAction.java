package me.m1dnightninja.midnightmenus.api.menu;

import me.m1dnightninja.midnightcore.api.config.ConfigSection;
import me.m1dnightninja.midnightcore.api.config.ConfigSerializer;
import me.m1dnightninja.midnightcore.api.player.MPlayer;
import me.m1dnightninja.midnightcore.api.registry.MIdentifier;
import me.m1dnightninja.midnightmenus.api.MidnightMenusAPI;

public class MenuAction {

    private final MenuActionType type;
    private final String value;
    private final MenuRequirement requirement;

    public MenuAction(MenuActionType type, String value) {
        this.type = type;
        this.value = value;
        this.requirement = null;
    }

    public MenuAction(MenuActionType type, String value, MenuRequirement req) {
        this.type = type;
        this.value = value;
        this.requirement = req;
    }

    public void execute(MidnightMenu menu, MPlayer player) {

        if(requirement != null && !requirement.checkOrDeny(player)) return;
        type.execute(menu, player, value);

    }

    public static final ConfigSerializer<MenuAction> SERIALIZER = new ConfigSerializer<MenuAction>() {
        @Override
        public MenuAction deserialize(ConfigSection section) {

            MenuActionType type = MenuActionType.ACTION_TYPE_REGISTRY.get(MIdentifier.parseOrDefault(section.getString("type"), "midnightmenus"));
            String value = section.getString("value");

            MenuRequirement requirement = null;
            if(section.has("requirement", ConfigSection.class)) {
                requirement = MenuRequirement.SERIALIZER.deserialize(section.getSection("requirement"));
            }

            return new MenuAction(type, value, requirement);
        }

        @Override
        public ConfigSection serialize(MenuAction object) {
            return null;
        }
    };

}