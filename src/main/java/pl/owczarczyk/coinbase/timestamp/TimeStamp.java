package pl.owczarczyk.coinbase.timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TimeStamp {

    private Map<String, String> data;

}
