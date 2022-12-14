package com.ftl1ght.yygh.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ftl1ght.yygh.listener.DictListener;
import com.ftl1ght.yygh.mapper.DictMapper;
import com.ftl1ght.yygh.service.DictService;
import com.yygh.model.cmn.Dict;
import com.yygh.vo.cmn.DictEeVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author FTL1ght
 * @Description
 * @create 2022-11-23 16:22
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    //根据id查询子数据列表
    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向list集合每个dict对象中设置hasChildren
        for(Dict dict:dictList){
            Long dictId = dict.getId();
            boolean isChild = isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }

    @Override
    public void exportDictData(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "dict";
        //以下载方式打开
        response.setHeader("Content-disposition","attachment:filement="+fileName+".xlsx");

        //查询数据库
        List<Dict> dictList = baseMapper.selectList(null);
        //Dict-->DictEevo
        List<DictEeVo> dictVoList = new ArrayList<>();
        for (Dict dict:dictList){
            DictEeVo dictEeVo = new DictEeVo();
            //dictEeVo.setId(dict.getId())
            BeanUtils.copyProperties(dict,dictEeVo);
            dictVoList.add(dictEeVo);
        }
        //调用方法进行写操作
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                    .doWrite(dictVoList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importDict(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), DictEeVo.class,new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDictName(String dictCode, String value) {

        //如果dictCode为空
        if (StringUtils.isEmpty(dictCode)){
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value",value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();
        }else{
            //先根据dictCode查询dict对象，得到dict的id值
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("dict_code",dictCode);
            Dict codeDict = baseMapper.selectOne(wrapper);
            Long parent_id  = codeDict.getId();
            //根据parent_id和value进行查询
            Dict finalDict = baseMapper.selectOne(new QueryWrapper<Dict>()
                    .eq("parent_id", parent_id)
                    .eq("value", value));

            return finalDict.getName();

        }
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {

        //根据dictCode获取id
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code",dictCode);
        Dict dict = baseMapper.selectOne(wrapper);
        //根据id获取下层子节点
        List<Dict> childData = this.findChildData(dict.getId());

        return childData;
    }

    //判断id下是否有子节点
    private boolean isChildren(Long id){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        return count>0;
    }
}
