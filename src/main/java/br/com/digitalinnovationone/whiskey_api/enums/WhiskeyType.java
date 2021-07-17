package br.com.digitalinnovationone.whiskey_api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WhiskeyType {

    SCOTCH("Scotch"),
    WOODFORD("Woodford Reserve"),
    BULLEIT("Frontier Whiskey"),
    JIM_BEAN("Jim Beam"),
    BUFFALO_TRACE("Buffalo Trace"),
    WILD_TURKEY("Bild Turkey");
	
    private final String description;
}
