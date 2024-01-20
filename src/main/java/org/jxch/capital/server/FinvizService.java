package org.jxch.capital.server;

import org.jxch.capital.http.finviz.FinvizNewsDto;

import java.util.List;

public interface FinvizService {

    List<FinvizNewsDto> allNewsTitleTransToChinese();
    List<FinvizNewsDto> newsTitleTransToChinese();
    List<FinvizNewsDto> blogsTitleTransToChinese();

}
