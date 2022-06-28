package org.starcoin.bcs.sab;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.starcoin.bcs.sab.BCSTraverser.*;

public class GetValueByIndexesHandler implements ContentHandler {

    public static final int[] ROOT_INDEXES = new int[0];

    private final int[] targetIndexes;

    /**
     * Current indexes of the current containers at depths.
     */
    private final List<Integer> currentIndexes;

    /**
     * Current values at depth offsets(relative to target depth).
     */
    private final List<Object> currentValues;

    /**
     * The current depth in the nested containers.
     * -1 means in root.
     */
    private int currentDepth = -1;

    private boolean targetReached;

    private Object targetValue;

    /**
     * @param targetIndexes Indexes of the target value at depths.
     */
    public GetValueByIndexesHandler(int[] targetIndexes) {
        this.targetIndexes = targetIndexes;
        this.currentIndexes = new ArrayList<>(this.targetIndexes.length);
        this.currentValues = new ArrayList<>();
    }

    /**
     * Add element to container and return container.
     */
    @SuppressWarnings("unchecked")
    private static Object addElementToContainerValue(Object element, Object container) {
        if (container instanceof List) {
            ((List<Object>) container).add(element);
            return container;
        } else if (container instanceof Optional) {
            return element != null ? Optional.of(element) : Optional.empty();
        } else if (container instanceof BCSTraverser.Enum) {
            BCSTraverser.Enum o = ((BCSTraverser.Enum) container);
            return new BCSTraverser.Enum(o.getVariantIndex(), element);
        } else {
            throw new IllegalArgumentException("container: " + container);
        }
    }

    public Object getTargetValue() {
        return targetValue;
    }

    @Override
    public boolean value(Object val) {
        increaseCurrentIndexAtCurrentDepth();
        if (this.targetReached) {
            setCurrentValueAtCurrentDepth(val);
            // non-container value is complete
            addCurrentValueToParentContainer();
        }
        return shouldBreakTraversal();
    }

    @Override
    public void startContainer(String containerType, String elementType) {
        increaseCurrentIndexAtCurrentDepth();
        if (this.targetReached) {
            setCurrentValueAtCurrentDepth(createContainerValue(containerType));
        }
        increaseCurrentDepth();
    }

    @Override
    public void variantIndex(int value) {
        // ---------------------------------------------
        // It is here after received startContainer...
        //System.out.println("Variant Index: " + value);
        if (this.targetReached) {
            setCurrentParentEnumVariantIndex(value);
        }
    }

    @Override
    public boolean endContainer(String containerType, String elementType) {
        decreaseCurrentDepth();
        if (this.targetReached) {
            // current container value is complete, add to parent
            addCurrentValueToParentContainer();
        }
        return shouldBreakTraversal();
    }

    private void increaseCurrentDepth() {
        this.currentDepth++;
        //System.out.println("Current depth: " + this.currentDepth);
        ensureCurrentIndexesSize();
    }

    private void decreaseCurrentDepth() {
        clearCurrentIndexAtCurrentDepth();
        this.currentDepth--;
        //System.out.println("Current depth: " + this.currentDepth);
    }

    private void clearCurrentIndexAtCurrentDepth() {
        if (this.currentDepth >= this.currentIndexes.size()) {
            throw new IllegalStateException("this.currentDepth >= this.currentIndexes.size()");
        }
        this.currentIndexes.set(this.currentDepth, -1);
        //System.out.println("-- clearCurrentIndex, Current indexes: " + this.currentIndexes);
    }

    /**
     * Add current value at current depth to parent container.
     */
    private void addCurrentValueToParentContainer() {
        if (!targetReached) {
            throw new IllegalStateException("!this.targetReached");
        }
        int idx = currentDepthIndexOfCurrentValues();
        if (idx >= 1) {
            Object elementVal = this.currentValues.get(idx);
            // this.currentValues.set(idx, null);// can set to null.
            Object containerVal = this.currentValues.get(idx - 1);
            this.currentValues.set(idx - 1, addElementToContainerValue(elementVal, containerVal));
            //System.out.println("-- addCurrentValueToParentContainer, Current values: " + this.currentValues);
        }
    }

    /**
     * Set variant index of current parent enum of value at current depth.
     *
     * @param variantIndex variant index
     */
    private void setCurrentParentEnumVariantIndex(int variantIndex) {
        if (!targetReached) {
            throw new IllegalStateException("!this.targetReached");
        }
        int idx = currentDepthIndexOfCurrentValues();
        if (idx >= 1) {
            Object containerVal = this.currentValues.get(idx - 1);
            if (!(containerVal instanceof BCSTraverser.Enum)) {
                throw new IllegalStateException("Current parent container is not an enum.");
            }
            //BCSTraverser.Enum enumVal = (BCSTraverser.Enum)containerVal;
            this.currentValues.set(idx - 1, new BCSTraverser.Enum(variantIndex));
            //System.out.println("-- setCurrentEnumVariantIndex, Current values: " + this.currentValues);
        }
    }

    private void increaseCurrentIndexAtCurrentDepth() {
        if (this.currentDepth >= 0) {
            ensureCurrentIndexesSize();
            this.currentIndexes.set(this.currentDepth, this.currentIndexes.get(this.currentDepth) + 1);
            //System.out.println("-- increaseCurrentIndexAtCurrentDepth, Current indexes: " + this.currentIndexes);
            //System.out.println("-- current depth: " + this.currentDepth);
            //System.out.println("-- Current values: " + this.currentValues);
        }
        if (!this.targetReached && checkIfTargetReached()) {
            this.targetReached = true;
        }
    }

    private void ensureCurrentIndexesSize() {
        if (this.currentDepth >= this.currentIndexes.size()) {
            this.currentIndexes.add(this.currentDepth, -1);
        }
    }

    private void setCurrentValueAtCurrentDepth(Object val) {
        if (!this.targetReached) {
            throw new IllegalStateException("!this.targetReached");
        }
        int idx = currentDepthIndexOfCurrentValues();
        if (idx >= this.currentValues.size()) {
            this.currentValues.add(idx, null);
        }
        this.currentValues.set(idx, val);

        //System.out.println("-- current depth: " + this.currentDepth);
        //System.out.println("-- setCurrentValue, Current values: " + this.currentValues);
    }

    private int currentDepthIndexOfCurrentValues() {
        return this.currentDepth - this.targetIndexes.length + 1;
    }

    /**
     * If return true, target value is also set.
     *
     * @return Return true if back to target indexes.
     */
    private boolean shouldBreakTraversal() {
        boolean b = this.targetReached
                // && checkIfTargetReached()
                && this.currentDepth == this.targetIndexes.length - 1;
        if (b) {
            this.targetValue = this.currentValues.get(currentDepthIndexOfCurrentValues());
        }
        return b;
    }

    private boolean checkIfTargetReached() {
        if (this.currentDepth != this.targetIndexes.length - 1) {
            return false;
        }
        for (int i = 0; i < this.targetIndexes.length; i++) {
            if (this.currentIndexes.get(i) != this.targetIndexes[i]) {
                return false;
            }
        }
        return true;
    }

    private Object createContainerValue(String containerType) {
        Object container;
        if (TYPE_VECTOR.equals(containerType) || TYPE_STRUCT.equals(containerType)) {
            container = new ArrayList<>();
        } else if (TYPE_OPTIONAL.equals(containerType)) {
            container = Optional.empty();
        } else if (TYPE_ENUM.equals(containerType)) {
            container = BCSTraverser.Enum.EMPTY;
        } else {
            throw new IllegalArgumentException("containerType: " + containerType);
        }
        return container;
    }
}
