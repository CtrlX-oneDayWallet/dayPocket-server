package hyu.dayPocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssetDto {

    private Long asset;
    private Integer targetReceiptFiPoint;
    private Integer receiptFiPoint;
    private Double processPoint;
    private Integer leftPoint;
    private Integer fiPoint;

    public static AssetDto assetFrom(Long asset, Integer targetReceiptFiPoint, Integer receiptFiPoint,
                                     Double processPoint, Integer leftPoint, Integer fiPoint) {
        return new AssetDto(asset, targetReceiptFiPoint, receiptFiPoint, processPoint, leftPoint, fiPoint);

    }
}
