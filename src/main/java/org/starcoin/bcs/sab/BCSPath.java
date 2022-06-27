package org.starcoin.bcs.sab;

import com.novi.bcs.BcsDeserializer;
import com.novi.serde.DeserializationError;
import org.starcoin.bcs.sab.TypeParser.Pair;

import java.util.List;

public class BCSPath {
    private BCSPath() {
    }

    public static Object select(byte[] bytes, String typeAndPath) throws DeserializationError, ParseException {
        Pair<BCSTraverser, List<Integer>> tp = new TypeParser(typeAndPath).typeAndPath();
        BCSTraverser traverser = tp.getItem1();
        List<Integer> path = tp.getItem2();
        GetValueByIndexesHandler vg = new GetValueByIndexesHandler(path.stream().mapToInt(Integer::valueOf).toArray());
        traverser.traverse(new BcsDeserializer(bytes), vg);
        return vg.getTargetValue();
    }
}
