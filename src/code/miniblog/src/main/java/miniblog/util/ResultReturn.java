package miniblog.util;

import miniblog.constant.CommonConstant;

public class ResultReturn {

    private String text;
    private String page;
    private int type;

    public ResultReturn(String text, String page,int type) {
        this.text = text;
        this.page = page;
        this.type = type;
    }

    public ResultReturn() {
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getPage()
    {
        return page;
    }

    public void setPage(String page)
    {
        this.page = page;
    }

    
    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        if (type == CommonConstant.DIALOG_SUCCESS)
        {
            return "<script type='text/javascript'>alert('"+getText()+"');window.location.href = '"+getPage()+"';</script>";
        } 
        if (type == CommonConstant.MESS_FAILD)
        {
            return "<div class='alert alert-danger' role='alert'>"+getText()+"</div>";
        }
        if (type == CommonConstant.MESS_SUCCESS)
        {
            return "<div class='alert alert-success' role='alert'>"+getText()+"</div>";
        }
        return null;
    }

}
