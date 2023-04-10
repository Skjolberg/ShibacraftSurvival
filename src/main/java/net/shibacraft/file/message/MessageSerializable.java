package net.shibacraft.file.message;

import net.shibacraft.library.chat.SLTextColor;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ConfigSerializable
public class MessageSerializable {

    private String prefix = "&8[&dShibacraft&8]&r";
    private String consoleSender = "{prefix} &cNo puedes usar este comando desde consola.";
    private String noPermission = "{prefix} &cNo tienes permiso para usar este comando.";
    private String invalidArgument = "{prefix} &cArgumento inválido.";
    private String pluginReload = "{prefix} &aEl plugin ha sido recargado.";
    private String noArguments = "{prefix} &cEste comando necesita argumentos.";
    private String cityDeleteAll = "{prefix} &aSe han borrado todas las ciudades.";
    private String discord = "{prefix} &eHaz clic en el enlace &c-> &b&ldiscord.shibacraft.net";
    private String map = "{prefix} &eHaz clic en el enlace &c-> &b&lmapa.shibacraft.net";
    private String web = "{prefix} &eHaz clic en el enlace &c-> &b&lshibacraft.net";
    private String wiki = "{prefix} &eHaz clic en el enlace &c-> &b&lshibacraft.net/wiki/";
    private String noChangeNameCity = "{prefix} &cNo puedes cambiar el nombre de la ciudad.";
    private String citySuccessfullyAdded = "{prefix} &aCiudad añadida con éxito.";
    private String cityAlreadyAdded = "{prefix} &cEsa ciudad ya existe.";
    private String cityNotExist = "{prefix} &cEsa ciudad no existe.";
    private String invitationSent = "{prefix} &aInvitación enviada a &5{citizen}.";
    private String citizenPending = "{prefix} &5{citizen}&c ya ha sido invitado por tí.";
    private String citizenPendingOther = "{prefix} &5{citizen}&c ya ha sido invitado por otra ciudad.";
    private String hasBeenInvited = "{prefix} &aHas sido invitado por &5{president}&a para unirte a &b{city}&a. " +
            "Escribe en el chat &bconfirmar&a para unirte. Tienes &e15&a segundos para responder sino la invitación " +
            "quedará invalidada.";
    private String invitationAccepted = "{prefix} &a¡Has aceptado la invitación para unirte a &5{city}&a!";
    private String citySuccessfullyEliminated = "{prefix} &a¡Se ha eliminado con éxito la ciudad y los ciudadanos!";
    private String hasAcceptedInvitation = "{prefix} &a¡&e{citizen}&a ha aceptado la invitación!";
    private String citizenListHeader = "&5&m----&r &b&nCiudadanos&r &5&m----";
    private String citizenListFooter = "&5&m-------------------";
    private String listEmpty = "{prefix} &cNo hay ciudadanos en tu ciudad.";
    private String maxCitizens = "{prefix} &cHas añadido el número máximo de ciudadanos.";
    private String alreadyHasCity = "{prefix} &cYa tienes una ciudad!";
    private String citizenAlreadyHasCity = "{prefix} &cEse ciudadano ya pertenece a una ciudad.";
    private String citizenNoFromYourCity = "{prefix} &cEste usuario no es de tu ciudad.";
    private String citizenKicked = "{prefix} &cCiudadano expulsado con éxito.";
    private String citizenAbandon = "{prefix} &cHas abandonado&a {city}";
    private String createCityFirst = "{prefix} &cDebes crear una ciudad primero.";
    private String offline = "{prefix} &cEse usuario está offline.";
    private String userNoCitizen = "{prefix} &cNo eres ciudadano de ninguna ciudad.";
    private String invalidCityName = "{prefix} &cLa ciudad contiene un formato no válido. No puedes poner &b&&c seguido " +
            "de los siguientes carácteres &b(k,l,m,n,o)&c. Solo se permite formatos de color.";
    private String invalidCityLength = "{prefix} &cEl nombre de ciudad debe contener de 3 a 15 carácteres.";
    private String citizenKickedFromPresident = "{prefix} &cHas sido expulsado de la ciudad &b{city}";
    private List<String> usagePresident = Arrays.asList(
            "&m&l-------------&e&l Presidente &r&m&l-------------",
            " ",
            "&5/presidente add city <ciudad>&f: Estableces el nombre de la ciudad.",
            "&5/presidente add user <player>&f: Añades un ciudadano a tu ciudad.",
            "&5/presidente remove user <player>&f: Expulsas un ciudadano de tu ciudad.",
            "&5/presidente remove city&f: Elimina tu ciudad y ciudadanos.",
            "&5/presidente list&f: Te muestra los ciudadanos de tu ciudad.",
            " ",
            "&m&l-------------------------------------");
    private List<String> usageCitizen = Arrays.asList(
            "&m&l-------------&e&l Ciudadano &r&m&l--------------",
            " ",
            "&5/ciudadano abandon&f: Abandonas la ciudad actual.",
            " ",
            "&m&l-------------------------------------");


    public String getPrefix() {
        return prefix;
    }

    public String getConsoleSender() {
        return colorAndPrefix(consoleSender);
    }

    public String getNoPermission() {
        return colorAndPrefix(noPermission);
    }

    public String getInvalidArgument() {
        return colorAndPrefix(invalidArgument);
    }

    public String getPluginReload() {
        return colorAndPrefix(pluginReload);
    }

    public String getNoArguments() {
        return colorAndPrefix(noArguments);
    }

    public String getCityDeleteAll() {
        return colorAndPrefix(cityDeleteAll);
    }

    public String getDiscord() {
        return colorAndPrefix(discord);
    }

    public String getMap() {
        return colorAndPrefix(map);
    }

    public String getWeb() {
        return colorAndPrefix(web);
    }

    public String getWiki() {
        return colorAndPrefix(wiki);
    }

    public String getNoChangeNameCity() {
        return colorAndPrefix(noChangeNameCity);
    }

    public String getCitySuccessfullyAdded() {
        return colorAndPrefix(citySuccessfullyAdded);
    }

    public String getCityAlreadyAdded() {
        return colorAndPrefix(cityAlreadyAdded);
    }

    public String getCityNotExist() {
        return colorAndPrefix(cityNotExist);
    }

    public String getInvitationSent() {
        return colorAndPrefix(invitationSent);
    }

    public String getCitizenPending() {
        return colorAndPrefix(citizenPending);
    }

    public String getCitizenPendingOther() {
        return colorAndPrefix(citizenPendingOther);
    }

    public String getHasBeenInvited() {
        return colorAndPrefix(hasBeenInvited);
    }

    public String getInvitationAccepted() {
        return colorAndPrefix(invitationAccepted);
    }

    public String getCitySuccessfullyEliminated() {
        return colorAndPrefix(citySuccessfullyEliminated);
    }

    public String getHasAcceptedInvitation() {
        return colorAndPrefix(hasAcceptedInvitation);
    }

    public String getCitizenListHeader() {
        return colorAndPrefix(citizenListHeader);
    }

    public String getCitizenListFooter() {
        return colorAndPrefix(citizenListFooter);
    }

    public String getListEmpty() {
        return colorAndPrefix(listEmpty);
    }

    public String getMaxCitizens() {
        return colorAndPrefix(maxCitizens);
    }

    public String getAlreadyHasCity() {
        return colorAndPrefix(alreadyHasCity);
    }

    public String getCitizenAlreadyHasCity() {
        return colorAndPrefix(citizenAlreadyHasCity);
    }

    public String getCitizenNoFromYourCity() {
        return colorAndPrefix(citizenNoFromYourCity);
    }

    public String getCitizenKicked() {
        return colorAndPrefix(citizenKicked);
    }

    public String getCitizenAbandon() {
        return colorAndPrefix(citizenAbandon);
    }

    public String getCreateCityFirst() {
        return colorAndPrefix(createCityFirst);
    }

    public String getOffline() {
        return colorAndPrefix(offline);
    }

    public String getUserNoCitizen() {
        return colorAndPrefix(userNoCitizen);
    }

    public String getInvalidCityName() {
        return colorAndPrefix(invalidCityName);
    }

    public String getInvalidCityLength() {
        return colorAndPrefix(invalidCityLength);
    }

    public String getCitizenKickedFromPresident() {
        return colorAndPrefix(citizenKickedFromPresident);
    }

    public List<String> getUsagePresident() {
        return usagePresident.stream().map(this::colorAndPrefix).collect(Collectors.toList());
    }

    public List<String> getUsageCitizen() {
        return usageCitizen.stream().map(this::colorAndPrefix).collect(Collectors.toList());
    }

    private String colorAndPrefix(String str){
        return SLTextColor.color(str.replace("{prefix}", prefix));
    }

}
