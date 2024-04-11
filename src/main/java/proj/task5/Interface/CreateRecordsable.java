package proj.task5.Interface;

import java.util.List;

public interface CreateRecordsable {

    <T, K> T create_rec_table(K model);

    <T, K> List<T> create_recs_table(K model);

}
