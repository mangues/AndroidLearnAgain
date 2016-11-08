package mangues.com.rxandroiddemo;

import java.util.List;

/**
 * Created by mangues on 16/11/1.
 */

public class Event {
    /** 列表加载事件 */
    public static class ItemListEvent
    {
        private List<Item> items;

        public ItemListEvent(List<Item> items)
        {
            this.items = items;
        }

        public List<Item> getItems()
        {
            return items;
        }
    }

}
