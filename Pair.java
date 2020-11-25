/**
 * Interface for storing a key, value pair
 */
public interface Pair<Key, Value> {

    /**
     * returns key
     */
    public Key getKey();

    /**
     * returns value
     */
    public Value getValue();

}
