package model;

import burp.IParameter;
import burp.IRequestInfo;

import java.net.URL;
import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/7/2 下午8:36
 */
public class IRequestIn implements IRequestInfo {
    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public List<String> getHeaders() {
        return null;
    }

    @Override
    public List<IParameter> getParameters() {
        return null;
    }

    @Override
    public int getBodyOffset() {
        return 0;
    }

    @Override
    public byte getContentType() {
        return 0;
    }
}
