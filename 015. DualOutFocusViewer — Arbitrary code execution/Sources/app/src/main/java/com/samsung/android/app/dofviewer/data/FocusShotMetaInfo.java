package com.samsung.android.app.dofviewer.data;

import com.samsung.android.app.livefocusviewercommonlib.utils.DataUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;

public class FocusShotMetaInfo {
    public static final int DUAL_CAL_SIZE = 760;
    public static final int DUAL_TOF_CAL_SIZE = 1520;
    private static final String TAG = "DOFV_FocusShotMetaInfo";
    public FirstHeader mDofHeader1 = new FirstHeader();
    public SecondHeader mDofHeader2 = new SecondHeader();
    private byte[] metaBuffer;

    public static class FirstHeader {
        public int header_length;
        public int signature;
        public int ver_major;
        public int ver_minor;
    }

    public static class SecondHeader {
        public String keyNames;
        public String keyTypes;
        public int len1;
        public int len2;
        public int mAIBokeh;
        public int mApplyBeautyFace;
        public int mArtBokehParam0;
        public int mArtBokehParam1;
        public int mArtBokehParam2;
        public int mBackdropType;
        public String mBaseVersion = "";
        public int mBlurLevel;
        public int mBlurLevelBar;
        public int mBlurLevelIntensity;
        public int mBlurLevelVer;
        public float mBokehThreshold;
        public int mBokehType;
        public int mBrightnessValue;
        public int mCalSize;
        public float mCalavgerr;
        public float mCalmaxerr;
        public float mCalrange;
        public int mCameraID;
        public short mCaptureMode;
        public int mChipsetVendor;
        public int[] mDepthBits = new int[2];
        public int mDepthCalDisparity;
        public int mDepthCameraType;
        public String mDepthLibVersion = "";
        public int[] mDepthMapCompressed = new int[2];
        public int[] mDepthMapHeight = new int[2];
        public int[] mDepthMapWidth = new int[2];
        public int mDepthMaxDisparity;
        public int mDepthMinDisparity;
        public int mDepthSolutionProvider = 3;
        public int mDeviceOrientation;
        public int mDollyZoom;
        public short mDummyFlag;
        public int mEffect;
        public int[] mFGPosition = new int[2];
        public float mFocusRange;
        public String mHDRVersion = "";
        public short mHiddenFocusBottom;
        public short mHiddenFocusLeft;
        public short mHiddenFocusRight;
        public short mHiddenFocusTop;
        public int mImageFlip;
        public int[] mInputFileNameSize = new int[2];
        public String[] mInputFrameNameList = new String[]{"", ""};
        public int mInputMainHeight;
        public int mInputMainWidth;
        public int mInputSubHeight;
        public int mInputSubWidth;
        public int mLLSApply;
        public int mLLSTuneParam0;
        public int mLLSTuneParam1;
        public int mLLSTuneParam2;
        public int mLLSTuneParam3;
        public String mLibVersion = "";
        public int mMainISO;
        public String[] mMapName = new String[]{"", ""};
        public int mNumberOfFaces;
        public int mNumberOfInputs;
        public int mObjectOrientation;
        public int mOutputHeight;
        public int mOutputWidth;
        public int mProcessMode;
        public int mRetouchLevel;
        public int mSkinColorLevel;
        public int mSkinToneLevel;
        public int mSolutionModel;
        public int mSubISO;
        public int mSupportBlurStrength;
        public int mSupportRefocus;
        public int mSupportZoomInOut;
        public int mSwitchInput;
        public float mTOFCalavgerr;
        public float mTOFCalmaxerr;
        public float mTOFCalrange;
        public short mTOFDummyFlag;
        public String mVersion = "1";
        public int[][] mFaceInfo = (int[][]) Array.newInstance(int.class, 6, 13);
        public byte[] mBlurLevelTable = new byte[8];
    }

    public FocusShotMetaInfo() {
    }

    public FocusShotMetaInfo(byte[] bArr) {
        this.metaBuffer = bArr;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        this.mDofHeader1.signature = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader1.ver_major = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader1.ver_minor = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader1.header_length = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mProcessMode = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mLibVersion = DataUtils.readString(byteArrayInputStream, 128);
        this.mDofHeader2.mNumberOfInputs = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mInputFileNameSize = new int[]{DataUtils.readInt(byteArrayInputStream), DataUtils.readInt(byteArrayInputStream)};
        String[] strArr = {DataUtils.readString(byteArrayInputStream, 128), DataUtils.readString(byteArrayInputStream, 128)};
        SecondHeader secondHeader = this.mDofHeader2;
        secondHeader.mInputFrameNameList = strArr;
        secondHeader.len1 = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.len2 = DataUtils.readInt(byteArrayInputStream);
        String[] strArr2 = {DataUtils.readStringNoskip(byteArrayInputStream, 32), DataUtils.readStringNoskip(byteArrayInputStream, 32)};
        SecondHeader secondHeader2 = this.mDofHeader2;
        secondHeader2.mMapName = strArr2;
        secondHeader2.mHDRVersion = DataUtils.readStringNoskip(byteArrayInputStream, 64);
        this.mDofHeader2.mBaseVersion = DataUtils.readStringNoskip(byteArrayInputStream, 64);
        this.mDofHeader2.mBlurLevelVer = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mBlurLevelBar = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mBlurLevelIntensity = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mBlurLevelTable[0] = DataUtils.readByte(byteArrayInputStream);
        this.mDofHeader2.mBlurLevelTable[1] = DataUtils.readByte(byteArrayInputStream);
        this.mDofHeader2.mBlurLevelTable[2] = DataUtils.readByte(byteArrayInputStream);
        this.mDofHeader2.mBlurLevelTable[3] = DataUtils.readByte(byteArrayInputStream);
        this.mDofHeader2.mBlurLevelTable[4] = DataUtils.readByte(byteArrayInputStream);
        this.mDofHeader2.mBlurLevelTable[5] = DataUtils.readByte(byteArrayInputStream);
        this.mDofHeader2.mBlurLevelTable[6] = DataUtils.readByte(byteArrayInputStream);
        this.mDofHeader2.mBlurLevelTable[7] = DataUtils.readByte(byteArrayInputStream);
        this.mDofHeader2.mVersion = DataUtils.readStringNoskip(byteArrayInputStream, 44);
        this.mDofHeader2.mSupportRefocus = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mSupportZoomInOut = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mSupportBlurStrength = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mOutputWidth = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mOutputHeight = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mDeviceOrientation = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mImageFlip = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mCameraID = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mNumberOfFaces = DataUtils.readInt(byteArrayInputStream);
        for (int i = 0; i < 6; i++) {
            for (int i2 = 0; i2 < 13; i2++) {
                this.mDofHeader2.mFaceInfo[i][i2] = DataUtils.readInt(byteArrayInputStream);
            }
        }
        int[] iArr = {DataUtils.readInt(byteArrayInputStream), DataUtils.readInt(byteArrayInputStream)};
        SecondHeader secondHeader3 = this.mDofHeader2;
        secondHeader3.mFGPosition = iArr;
        secondHeader3.mFocusRange = DataUtils.readFloat(byteArrayInputStream);
        this.mDofHeader2.mBlurLevel = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mDepthSolutionProvider = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mDepthCameraType = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mDepthMinDisparity = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mDepthMaxDisparity = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mDepthCalDisparity = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mDepthLibVersion = DataUtils.readStringNoskip(byteArrayInputStream, 117);
        this.mDofHeader2.mCaptureMode = DataUtils.readShort(byteArrayInputStream);
        this.mDofHeader2.mHiddenFocusLeft = DataUtils.readShort(byteArrayInputStream);
        this.mDofHeader2.mHiddenFocusTop = DataUtils.readShort(byteArrayInputStream);
        this.mDofHeader2.mHiddenFocusRight = DataUtils.readShort(byteArrayInputStream);
        this.mDofHeader2.mHiddenFocusBottom = DataUtils.readShort(byteArrayInputStream);
        int i3 = byteArrayInputStream.skip(1L) == 0 ? 1 : 0;
        this.mDofHeader2.mDepthMapCompressed = new int[]{DataUtils.readInt(byteArrayInputStream), DataUtils.readInt(byteArrayInputStream)};
        this.mDofHeader2.mDepthMapWidth = new int[]{DataUtils.readInt(byteArrayInputStream), DataUtils.readInt(byteArrayInputStream)};
        this.mDofHeader2.mDepthMapHeight = new int[]{DataUtils.readInt(byteArrayInputStream), DataUtils.readInt(byteArrayInputStream)};
        int[] iArr2 = {DataUtils.readInt(byteArrayInputStream), DataUtils.readInt(byteArrayInputStream)};
        SecondHeader secondHeader4 = this.mDofHeader2;
        secondHeader4.mDepthBits = iArr2;
        secondHeader4.mApplyBeautyFace = DataUtils.readInt(byteArrayInputStream);
        this.mDofHeader2.mSkinToneLevel = DataUtils.readInt(byteArrayInputStream);
        if (this.mDofHeader1.ver_major == 2) {
            this.mDofHeader2.mSolutionModel = DataUtils.readInt(byteArrayInputStream);
            this.mDofHeader2.mInputMainWidth = DataUtils.readInt(byteArrayInputStream);
            this.mDofHeader2.mInputMainHeight = DataUtils.readInt(byteArrayInputStream);
            this.mDofHeader2.mInputSubWidth = DataUtils.readInt(byteArrayInputStream);
            this.mDofHeader2.mInputSubHeight = DataUtils.readInt(byteArrayInputStream);
            this.mDofHeader2.mBokehType = DataUtils.readInt(byteArrayInputStream);
            this.mDofHeader2.mRetouchLevel = DataUtils.readInt(byteArrayInputStream);
            this.mDofHeader2.mSkinColorLevel = DataUtils.readInt(byteArrayInputStream);
            this.mDofHeader2.mBokehThreshold = DataUtils.readFloat(byteArrayInputStream);
            this.mDofHeader2.mEffect = DataUtils.readInt(byteArrayInputStream);
            this.mDofHeader2.mObjectOrientation = DataUtils.readInt(byteArrayInputStream);
            if (this.mDofHeader1.ver_minor >= 1) {
                this.mDofHeader2.mBrightnessValue = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mSwitchInput = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mDollyZoom = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mAIBokeh = DataUtils.readInt(byteArrayInputStream);
            }
            if (this.mDofHeader1.ver_minor >= 2) {
                this.mDofHeader2.mMainISO = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mSubISO = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mArtBokehParam0 = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mArtBokehParam1 = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mArtBokehParam2 = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mLLSApply = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mLLSTuneParam0 = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mLLSTuneParam1 = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mLLSTuneParam2 = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mLLSTuneParam3 = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mChipsetVendor = DataUtils.readInt(byteArrayInputStream);
                this.mDofHeader2.mBackdropType = DataUtils.readInt(byteArrayInputStream);
            }
            this.mDofHeader2.mCalSize = DataUtils.readInt(byteArrayInputStream);
            if (this.mDofHeader2.mCalSize == 760 || this.mDofHeader2.mCalSize == 1520) {
                i3 = byteArrayInputStream.skip(722L) == 0 ? i3 + 2 : i3;
                this.mDofHeader2.mCalrange = DataUtils.readFloat(byteArrayInputStream);
                this.mDofHeader2.mCalmaxerr = DataUtils.readFloat(byteArrayInputStream);
                this.mDofHeader2.mCalavgerr = DataUtils.readFloat(byteArrayInputStream);
                i3 = byteArrayInputStream.skip(21L) == 0 ? i3 + 4 : i3;
                this.mDofHeader2.mDummyFlag = DataUtils.readShort(byteArrayInputStream);
                if (byteArrayInputStream.skip(3L) == 0) {
                    i3 += 8;
                }
            }
            if (this.mDofHeader2.mCalSize == 1520) {
                i3 = byteArrayInputStream.skip(722L) == 0 ? i3 + 16 : i3;
                this.mDofHeader2.mTOFCalrange = DataUtils.readFloat(byteArrayInputStream);
                this.mDofHeader2.mTOFCalmaxerr = DataUtils.readFloat(byteArrayInputStream);
                this.mDofHeader2.mTOFCalavgerr = DataUtils.readFloat(byteArrayInputStream);
                i3 = byteArrayInputStream.skip(21L) == 0 ? i3 + 32 : i3;
                this.mDofHeader2.mTOFDummyFlag = DataUtils.readShort(byteArrayInputStream);
            }
        }
    }

    public byte[] generateMetadata() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataUtils.writeInt(out, this.mDofHeader1.signature);
            DataUtils.writeInt(out, this.mDofHeader1.ver_major);
            DataUtils.writeInt(out, this.mDofHeader1.ver_minor);
            DataUtils.writeInt(out, this.mDofHeader1.header_length);
            DataUtils.writeInt(out, this.mDofHeader2.mProcessMode);
            DataUtils.writeString(out, 128, this.mDofHeader2.mLibVersion);
            DataUtils.writeInt(out, this.mDofHeader2.mNumberOfInputs);

            DataUtils.writeInt(out, this.mDofHeader2.mInputFileNameSize[0]);
            DataUtils.writeInt(out, this.mDofHeader2.mInputFileNameSize[1]);

            DataUtils.writeString(out, 128, this.mDofHeader2.mInputFrameNameList[0]);
            DataUtils.writeString(out, 128, this.mDofHeader2.mInputFrameNameList[1]);

            DataUtils.writeInt(out, this.mDofHeader2.len1);
            DataUtils.writeInt(out, this.mDofHeader2.len2);

            DataUtils.writeString(out, 32, this.mDofHeader2.mMapName[0]);
            DataUtils.writeString(out, 32, this.mDofHeader2.mMapName[1]);

            DataUtils.writeString(out, 64, this.mDofHeader2.mHDRVersion);
            DataUtils.writeString(out, 64, this.mDofHeader2.mBaseVersion);

            DataUtils.writeInt(out, this.mDofHeader2.mBlurLevelVer);
            DataUtils.writeInt(out, this.mDofHeader2.mBlurLevelBar);
            DataUtils.writeInt(out, this.mDofHeader2.mBlurLevelIntensity);

            DataUtils.writeByte(out, this.mDofHeader2.mBlurLevelTable[0]);
            DataUtils.writeByte(out, this.mDofHeader2.mBlurLevelTable[1]);
            DataUtils.writeByte(out, this.mDofHeader2.mBlurLevelTable[2]);
            DataUtils.writeByte(out, this.mDofHeader2.mBlurLevelTable[3]);
            DataUtils.writeByte(out, this.mDofHeader2.mBlurLevelTable[4]);
            DataUtils.writeByte(out, this.mDofHeader2.mBlurLevelTable[5]);
            DataUtils.writeByte(out, this.mDofHeader2.mBlurLevelTable[6]);
            DataUtils.writeByte(out, this.mDofHeader2.mBlurLevelTable[7]);

            DataUtils.writeString(out, 44, this.mDofHeader2.mVersion);
            DataUtils.writeInt(out, this.mDofHeader2.mSupportRefocus);
            DataUtils.writeInt(out, this.mDofHeader2.mSupportZoomInOut);
            DataUtils.writeInt(out, this.mDofHeader2.mSupportBlurStrength);
            DataUtils.writeInt(out, this.mDofHeader2.mOutputWidth);
            DataUtils.writeInt(out, this.mDofHeader2.mOutputHeight);
            DataUtils.writeInt(out, this.mDofHeader2.mDeviceOrientation);
            DataUtils.writeInt(out, this.mDofHeader2.mImageFlip);
            DataUtils.writeInt(out, this.mDofHeader2.mCameraID);
            DataUtils.writeInt(out, this.mDofHeader2.mNumberOfFaces);
            for (int i = 0; i < 6; i++) {
                for (int i2 = 0; i2 < 13; i2++) {
                    DataUtils.writeInt(out, this.mDofHeader2.mFaceInfo[i][i2]);
                }
            }
            DataUtils.writeInt(out, this.mDofHeader2.mFGPosition[0]);
            DataUtils.writeInt(out, this.mDofHeader2.mFGPosition[1]);

            DataUtils.writeFloat(out, this.mDofHeader2.mFocusRange);
            DataUtils.writeInt(out, this.mDofHeader2.mBlurLevel);
            DataUtils.writeInt(out, this.mDofHeader2.mDepthSolutionProvider);
            DataUtils.writeInt(out, this.mDofHeader2.mDepthCameraType);
            DataUtils.writeInt(out, this.mDofHeader2.mDepthMinDisparity);
            DataUtils.writeInt(out, this.mDofHeader2.mDepthMaxDisparity);
            DataUtils.writeInt(out, this.mDofHeader2.mDepthCalDisparity);

            DataUtils.writeString(out, 117, this.mDofHeader2.mDepthLibVersion);
            DataUtils.writeShort(out, this.mDofHeader2.mCaptureMode);
            DataUtils.writeShort(out, this.mDofHeader2.mHiddenFocusLeft);
            DataUtils.writeShort(out, this.mDofHeader2.mHiddenFocusTop);
            DataUtils.writeShort(out, this.mDofHeader2.mHiddenFocusRight);
            DataUtils.writeShort(out, this.mDofHeader2.mHiddenFocusBottom);

            DataUtils.writeByte(out, (byte) 0);

            DataUtils.writeInt(out, this.mDofHeader2.mDepthMapCompressed[0]);
            DataUtils.writeInt(out, this.mDofHeader2.mDepthMapCompressed[1]);

            DataUtils.writeInt(out, this.mDofHeader2.mDepthMapWidth[0]);
            DataUtils.writeInt(out, this.mDofHeader2.mDepthMapWidth[1]);

            DataUtils.writeInt(out, this.mDofHeader2.mDepthMapHeight[0]);
            DataUtils.writeInt(out, this.mDofHeader2.mDepthMapHeight[1]);

            DataUtils.writeInt(out, this.mDofHeader2.mDepthBits[0]);
            DataUtils.writeInt(out, this.mDofHeader2.mDepthBits[1]);

            DataUtils.writeInt(out, this.mDofHeader2.mApplyBeautyFace);
            DataUtils.writeInt(out, this.mDofHeader2.mSkinToneLevel);
            if (this.mDofHeader1.ver_major == 2) {
                DataUtils.writeInt(out, this.mDofHeader2.mSolutionModel);
                DataUtils.writeInt(out, this.mDofHeader2.mInputMainWidth);
                DataUtils.writeInt(out, this.mDofHeader2.mInputMainHeight);
                DataUtils.writeInt(out, this.mDofHeader2.mInputSubWidth);
                DataUtils.writeInt(out, this.mDofHeader2.mInputSubHeight);
                DataUtils.writeInt(out, this.mDofHeader2.mBokehType);
                DataUtils.writeInt(out, this.mDofHeader2.mRetouchLevel);
                DataUtils.writeInt(out, this.mDofHeader2.mSkinColorLevel);
                DataUtils.writeFloat(out, this.mDofHeader2.mBokehThreshold);
                DataUtils.writeInt(out, this.mDofHeader2.mEffect);
                DataUtils.writeInt(out, this.mDofHeader2.mObjectOrientation);
                if (this.mDofHeader1.ver_minor >= 1) {
                    DataUtils.writeInt(out, this.mDofHeader2.mBrightnessValue);
                    DataUtils.writeInt(out, this.mDofHeader2.mSwitchInput);
                    DataUtils.writeInt(out, this.mDofHeader2.mDollyZoom);
                    DataUtils.writeInt(out, this.mDofHeader2.mAIBokeh);
                }
                if (this.mDofHeader1.ver_minor >= 2) {
                    DataUtils.writeInt(out, this.mDofHeader2.mMainISO);
                    DataUtils.writeInt(out, this.mDofHeader2.mSubISO);
                    DataUtils.writeInt(out, this.mDofHeader2.mArtBokehParam0);
                    DataUtils.writeInt(out, this.mDofHeader2.mArtBokehParam1);
                    DataUtils.writeInt(out, this.mDofHeader2.mArtBokehParam2);
                    DataUtils.writeInt(out, this.mDofHeader2.mLLSApply);
                    DataUtils.writeInt(out, this.mDofHeader2.mLLSTuneParam0);
                    DataUtils.writeInt(out, this.mDofHeader2.mLLSTuneParam1);
                    DataUtils.writeInt(out, this.mDofHeader2.mLLSTuneParam2);
                    DataUtils.writeInt(out, this.mDofHeader2.mLLSTuneParam3);
                    DataUtils.writeInt(out, this.mDofHeader2.mChipsetVendor);
                    DataUtils.writeInt(out, this.mDofHeader2.mBackdropType);
                }
                DataUtils.writeInt(out, this.mDofHeader2.mCalSize);
                if (this.mDofHeader2.mCalSize == 760 || this.mDofHeader2.mCalSize == 1520) {
                    DataUtils.writeByteArray(out, new byte[722]);

                    DataUtils.writeFloat(out, this.mDofHeader2.mCalrange);
                    DataUtils.writeFloat(out, this.mDofHeader2.mCalmaxerr);
                    DataUtils.writeFloat(out, this.mDofHeader2.mCalavgerr);

                    DataUtils.writeByteArray(out, new byte[21]);

                    DataUtils.writeShort(out, this.mDofHeader2.mDummyFlag);

                    DataUtils.writeByteArray(out, new byte[3]);
                }
                if (this.mDofHeader2.mCalSize == 1520) {
                    DataUtils.writeByteArray(out, new byte[722]);

                    DataUtils.writeFloat(out, this.mDofHeader2.mTOFCalrange);
                    DataUtils.writeFloat(out, this.mDofHeader2.mTOFCalmaxerr);
                    DataUtils.writeFloat(out, this.mDofHeader2.mTOFCalavgerr);

                    DataUtils.writeByteArray(out, new byte[21]);

                    DataUtils.writeShort(out, this.mDofHeader2.mTOFDummyFlag);
                }
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}