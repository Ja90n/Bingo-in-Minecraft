package com.ja90n.bingo.enums;

public enum CustomSkull {
    BUTTON("{SkullOwner:{Id:[I;-1565836069,2010530334,-1267084710,-1988678414],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzczZmIyYTI4ZjNiNjY1NDg2NDEyZGI3YjY2Y2RiN2ZjNWM3ZDMzZDc0ZTg1MGI5NDcyMmRkM2QxNGFhYSJ9fX0=\"}]}},display:{Name:'[{\"text\":\"BINGO\",\"italic\":false,\"color\":\"red\"}]'}}"),
    HOST_MENU("{SkullOwner:{Id:[I;-1380375401,-942455658,-1849187337,-384007618],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFmYWQ1ZDJiODIyZmNlOGQ1OWJjZTA4NDE0YjlmODdmMjdhYTlkNzdhNzM4MjNhNThkZDUxN2VhODBiMmE1In19fQ==\"}]}},display:{Name:'[{\"text\":\"Host menu\",\"italic\":false,\"color\":\"blue\"}]'}}"),
    B_BLACK("{SkullOwner:{Id:[I;-391101065,1272204594,-2083837408,-988635098],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWVjYTk4YmVmZDBkN2VmY2E5YjExZWJmNGIyZGE0NTljYzE5YTM3ODExNGIzY2RkZTY3ZDQwNjdhZmI4OTYifX19\"}]}},display:{Name:'[{\"text\":\"B\",\"italic\":false,\"color\":\"white\"}]'}}"),
    I_BLACK("{SkullOwner:{Id:[I;1505408877,896093856,-1188206766,293896512],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzE0OGE4ODY1YmM0YWZlMDc0N2YzNDE1MTM4Yjk2YmJiNGU4YmJiNzI2MWY0NWU1ZDExZDcyMTlmMzY4ZTQifX19\"}]}},display:{Name:'[{\"text\":\"I\",\"italic\":false,\"color\":\"white\"}]'}}"),
    N_BLACK("{SkullOwner:{Id:[I;-1344645285,-1406319228,-1945687972,-1461570268],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjEyYzdhZmVhNDhlNTMzMjVlNTEyOTAzOGE0NWFlYzUxYWZlMjU2YWJjYTk0MWI2YmM4MjA2ZmFlMWNlZiJ9fX0=\"}]}},display:{Name:'[{\"text\":\"N\",\"italic\":false,\"color\":\"white\"}]'}}"),
    G_BLACK("{SkullOwner:{Id:[I;2088598567,712525234,-1206705947,1116828067],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThjMzM2ZGVkZmUxOTdiNDM0YjVhYjY3OTg4Y2JlOWMyYzlmMjg1ZWMxODcxZmRkMWJhNDM0ODU1YiJ9fX0=\"}]}},display:{Name:'[{\"text\":\"G\",\"italic\":false,\"color\":\"white\"}]'}}"),
    O_BLACK("{SkullOwner:{Id:[I;-466803459,-1230812348,-1641444015,-957489349],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWMyNzIzNWRlM2E1NTQ2NmI2Mjc0NTlmMTIzMzU5NmFiNmEyMmM0MzVjZmM4OWE0NDU0YjQ3ZDMyYjE5OTQzMSJ9fX0=\"}]}},display:{Name:'[{\"text\":\"O\",\"italic\":false,\"color\":\"white\"}]'}}")
    ;

    private final String customData;
    CustomSkull(String customData) {
        this.customData = customData;
    }

    public String getCustomData() {
        return customData;
    }
}
