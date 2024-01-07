package org.jxch.capital.server;

import org.jxch.capital.http.vec.dto.VecParam;
import org.jxch.capital.http.vec.dto.VecRes;

public interface VecService {

    VecRes search(VecParam param);

}
