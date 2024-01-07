package org.jxch.capital.http.vec;

import org.jxch.capital.http.vec.dto.VecParam;
import org.jxch.capital.http.vec.dto.VecRes;

public interface VecApi {

    VecRes search(VecParam param);

}
