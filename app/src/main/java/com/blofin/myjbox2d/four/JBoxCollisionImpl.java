package com.blofin.myjbox2d.four;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.blofin.myjbox2d.R;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class JBoxCollisionImpl {
    private World mWorld;
    private int mWorldWidth, mWorldHeight;
    private ViewGroup viewGroup;
    private Random random = new Random();

    private float dt = 1f / 60f;
    private int velocityIterations = 3;
    private int positionIterations = 10;

    private int mProportion = 50;
    //刚体的密度
    private float mDensity = 0.6f;
    //刚体摩擦系数
    private float mFrictionRatio = 0.8f;
//    private float mFrictionRatio = 0f;
    //补偿系数
//    private float mRestitutionRatio = 0.6f;
    private float mRestitutionRatio = 0f;

    /**
     * dip转换px
     */
    public  int dip2px(Context context, float dpValue) {
        try {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale);
        } catch (Exception exception) {
        }
        return 0;
    }
    Context context;
    public JBoxCollisionImpl(ViewGroup viewGroup,Context context) {
        this.viewGroup = viewGroup;
        this.context = context;
        mDensity = viewGroup.getContext().getResources().getDisplayMetrics().density;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void createWorld() {
        if (mWorld == null) {

//            float bodyWidth = mappingView2Body(mWorldWidth);
//            float bodyHeight = mappingView2Body(mWorldHeight);
//            float bodyRatio = mappingView2Body(mProportion);
//            AABB aabb=new AABB();
//            aabb.lowerBound.set(new Vec2(-mWorldWidth, -mWorldHeight));
//            aabb.upperBound.set(new Vec2(mWorldWidth, mWorldHeight));


            mWorld = new World(new Vec2(0, 6.0f));
//            updateTopAndBottomBounds();
//            updateLeftAndRightBounds();

//            initCircleWorldBounds();
            updateAllBounds();
//            updateBottomBounds();
//            initCircleWorldBounds2();

            updateAllBoundsTwo();
//
        }
    }


    private void createWorldChild(boolean change) {
        if (viewGroup != null) {
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = viewGroup.getChildAt(i);
                if (!isBodyView(view) || change) {
                    createBody(view);
                }
            }
        }

    }

    private void createBody(View view) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;

        bodyDef.position.set(mappingView2Body(view.getX() + view.getWidth() / 2), mappingView2Body(view.getY() + view.getHeight() / 2));

        Shape shape = null;
        Boolean isCircle = (Boolean) view.getTag(R.id.wd_view_circle_tag);
        if (isCircle != null && isCircle) {
            shape = createCircleBody(view);
        } else {
            shape = createPolygonBody(view);
        }

        FixtureDef def = new FixtureDef();
        def.shape = shape;
        def.density = mDensity;
        def.friction = mFrictionRatio;
        def.restitution = mRestitutionRatio;

        Body body = mWorld.createBody(bodyDef);
        body.createFixture(def);
        view.setTag(R.id.wd_view_body_tag, body);

        body.setLinearVelocity(new Vec2(random.nextFloat(), random.nextFloat()));
    }

    public void onSizeChanged(int w, int h) {
        this.mWorldWidth = w;
        this.mWorldHeight = h;
    }

    public void onLayout(boolean changed) {
        createWorld();
        createWorldChild(changed);
    }
    private Paint paint;
    public void onDraw(Canvas canvas) {
        if (mWorld != null) {
            mWorld.step(dt, velocityIterations, positionIterations);
        }
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (isBodyView(view)) {
                view.setX(getViewX(view));
                view.setY(getViewY(view));
                view.setRotation(getViewRotaion(view));
            }
        }
        viewGroup.invalidate();
    }


    /**
     * 设置世界边界 圆形
     */
    private void initCircleWorldBounds() {
        int w=mWorldWidth-10;
        int h=mWorldHeight-10;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
//        bodyDef.position.set(0, 0);
//        bodyDef.position.set(-mappingView2Body(mWorldWidth/2), 0);
        bodyDef.position.set(mappingView2Body(w), -mappingView2Body(h)*2);
//        bodyDef.position.set(mappingView2Body(mWorldWidth/2), mappingView2Body(mWorldHeight)/2);
//        bodyDef.position.set(mappingView2Body(mWorldWidth/2), mappingView2Body(mWorldHeight));
//        bodyDef.position.set(mappingView2Body(mWorldWidth/2), mappingView2Body(mProportion)/2);
//        bodyDef.position.set(mappingView2Body(mWorldWidth/2),0);
//        bodyDef.position.set(mappingView2Body(mWorldWidth/2),mappingView2Body(mProportion));



        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(mappingView2Body(h*2));
//        circleShape.setRadius(w/2);
//        circleShape.setRadius(mappingView2Body(mWorldWidth/10*8));
//        circleShape.setRadius(mappingView2Body(mWorldWidth/2));
        FixtureDef def = new FixtureDef();
        def.shape = circleShape;
        def.density = mDensity;
        def.friction = mFrictionRatio;
        def.restitution = mRestitutionRatio;

        Body body = mWorld.createBody(bodyDef);
        body.createFixture(def);
    }
    /**
     * 设置世界边界 圆形
     */
    private void initCircleWorldBounds2() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;//设置零重力,零速度
        int w=mWorldWidth;
        int h=mWorldHeight;


//        bodyDef.position.set(0, 0);
        bodyDef.position.set(0, -mappingView2Body(w/2));
//        bodyDef.position.set(-mappingView2Body(w/2), -mappingView2Body(h/2));
//        bodyDef.position.set(0, -mappingView2Body(h)/2);
//        bodyDef.position.set(0, -mappingView2Body(h*2));
//        bodyDef.position.set(0, mappingView2Body(h)/2);
//        bodyDef.position.set(-mappingView2Body(w/4), -mappingView2Body(h/4));
        Body bodyTop = mWorld.createBody(bodyDef);//世界中创建刚体

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = mDensity;
        fixtureDef.friction = mFrictionRatio;
        fixtureDef.restitution = mRestitutionRatio;

        //设置圆形刚体边界
        ArrayList positions = polygon2(12, Double.valueOf(w/2).intValue());
//        ArrayList positions = polygon2(36, Double.valueOf(w).intValue());
        for (int i = 0; i < positions.size(); i++) {
            float[] xy = (float[]) positions.get(i);
            float x = xy[0];
            float y = xy[1];
            float segmentlength = xy[2];
            float angle = xy[3];
            PolygonShape polygonShape = new PolygonShape();
            // 设置具有方向的shape
            polygonShape.setAsBox(0, mappingView2Body(segmentlength), new Vec2(mappingView2Body(x), mappingView2Body(y)), angle);
            fixtureDef.shape = polygonShape;
            bodyTop.createFixture(fixtureDef);//刚体添加夹具
        }
    }

    private void updateAllBoundsTwo() {
        try {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.STATIC;//设置零重力,零速度
            float bodyWidth = mappingView2Body(mWorldWidth);
            float bodyHeight = mappingView2Body(mWorldHeight);
            float bodyHeight2 = mappingView2Body(mWorldHeight-dip2px(context,5));
            float bodyRatio = mappingView2Body(mProportion);
//            ChainShape polygonShape1 = new ChainShape();

//            Vec2[] vec2s = new Vec2[3];
//            vec2s[0] = new Vec2(0,bodyHeight-2);
//            vec2s[1] = new Vec2(bodyWidth/2,bodyHeight);
//            vec2s[2] = new Vec2(bodyWidth/2,bodyHeight-2f);
//            polygonShape1.createChain(vec2s,3);
//            polygonShape1.setPrevVertex(new Vec2(0,bodyHeight));
//            polygonShape1.setNextVertex(new Vec2(bodyWidth,bodyHeight));

            EdgeShape polygonShape1 = new EdgeShape();
            polygonShape1.set(new Vec2(0,bodyHeight2),new Vec2(bodyWidth/2,bodyHeight));


            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape1;
            fixtureDef.density = mDensity;
            fixtureDef.friction = mFrictionRatio;
            fixtureDef.restitution = mRestitutionRatio;


            Body bodyBottom = mWorld.createBody(bodyDef);//世界中创建刚体
            bodyBottom.createFixture(fixtureDef);



            EdgeShape polygonShape2 = new EdgeShape();
            polygonShape2.set(new Vec2(bodyWidth/2,bodyHeight),new Vec2(bodyWidth,bodyHeight2));


            FixtureDef fixtureDef2 = new FixtureDef();
            fixtureDef2.shape = polygonShape2;
            fixtureDef2.density = mDensity;
            fixtureDef2.friction = mFrictionRatio;
            fixtureDef2.restitution = mRestitutionRatio;


            Body bodyBottom2 = mWorld.createBody(bodyDef);//世界中创建刚体
            bodyBottom2.createFixture(fixtureDef2);



        } catch (Exception exception) {

        }
    }

    private void updateAllBounds() {
        try {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.STATIC;//设置零重力,零速度
            float bodyWidth = mappingView2Body(mWorldWidth);
            float bodyHeight = mappingView2Body(mWorldHeight);
            float bodyRatio = mappingView2Body(mProportion);
            PolygonShape polygonShape1 = new PolygonShape();
            polygonShape1.setAsBox(bodyWidth, bodyRatio);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = polygonShape1;
            fixtureDef.density = mDensity;
            fixtureDef.friction = mFrictionRatio;
            fixtureDef.restitution = mRestitutionRatio;

            bodyDef.position.set(0, -bodyRatio);
            Body bodyTop = mWorld.createBody(bodyDef);//世界中创建刚体
            bodyTop.createFixture(fixtureDef);//刚体添加夹具

            bodyDef.position.set(0, bodyHeight + bodyRatio);
            Body bodyBottom = mWorld.createBody(bodyDef);//世界中创建刚体
            bodyBottom.createFixture(fixtureDef);

            PolygonShape polygonShape2 = new PolygonShape();
            polygonShape2.setAsBox(bodyRatio, bodyHeight);
            FixtureDef fixtureDef2 = new FixtureDef();
            fixtureDef2.shape = polygonShape2;
            fixtureDef.density = mDensity;
            fixtureDef.friction = mFrictionRatio;
            fixtureDef.restitution = mRestitutionRatio;

//            bodyDef.position.set(-bodyRatio, bodyHeight);
            bodyDef.position.set(-bodyRatio, 0);
            Body bodyLeft = mWorld.createBody(bodyDef);//世界中创建刚体
            bodyLeft.createFixture(fixtureDef2);//刚体添加物理属性

            bodyDef.position.set(bodyWidth + bodyRatio, 0);
            Body bodyRight = mWorld.createBody(bodyDef);//世界中创建刚体
            bodyRight.createFixture(fixtureDef2);//刚体添加物理属性

        } catch (Exception exception) {

        }
    }


    private void updateTopAndBottomBounds() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        PolygonShape shape = new PolygonShape();
        float hx = mappingView2Body(mWorldWidth);
        float hy = mappingView2Body(mProportion);
        shape.setAsBox(hx, hy);

        FixtureDef def = new FixtureDef();
        def.shape = shape;
        def.density = mDensity;
        def.friction = mFrictionRatio;
        def.restitution = mRestitutionRatio;

        bodyDef.position.set(0, -hy);
        Body topBody = mWorld.createBody(bodyDef);
        topBody.createFixture(def);


        PolygonShape shape2 = new PolygonShape();
        shape2.setAsBox(hx, hy);
//        shape2.setAsBox(hx, hy,new Vec2(hx,hy),0.5f);
//        shape2.m_radius=2.0f;
        FixtureDef def2 = new FixtureDef();
        def2.shape = shape2;
        def2.density = mDensity;
        def2.friction = mFrictionRatio;
        def2.restitution = mRestitutionRatio;


        bodyDef.position.set(0, (mappingView2Body(mWorldHeight) + hy));
        Body bottomBody = mWorld.createBody(bodyDef);
        bottomBody.createFixture(def2);
    }

    private void updateBottomBounds() {
        float bodyWidth = mappingView2Body(mWorldWidth);
        float bodyHeight = mappingView2Body(mWorldHeight);
        float bodyRatio = mappingView2Body(mProportion);



//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyType.STATIC;//设置零重力,零速度
////        bodyDef.position.set(0,0);
//        Body bodyBottomLeft = mWorld.createBody(bodyDef);//世界中创建刚体
//
//        FixtureDef def = new FixtureDef();
//        def.density = mDensity;
//        def.friction = mFrictionRatio;
//        def.restitution = mRestitutionRatio;
//        float x = mWorldWidth-20;
//        float y = mWorldHeight-50;
//        float segmentlength = mWorldWidth;
//        float angle = 4f;
////        float angle = 0f;
//        PolygonShape polygonShape = new PolygonShape();
//        // 设置具有方向的shape
//        polygonShape.setAsBox(0, mappingView2Body(segmentlength), new Vec2(mappingView2Body(x), mappingView2Body(y)), angle);
//        def.shape = polygonShape;
//        bodyBottomLeft.createFixture(def);//刚体添加夹具



    }

    private void updateLeftAndRightBounds() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        PolygonShape shape = new PolygonShape();
        float hx = mappingView2Body(mProportion);
        float hy = mappingView2Body(mWorldHeight);
        shape.setAsBox(hx, hy);

        FixtureDef def = new FixtureDef();
        def.shape = shape;
        def.density = mDensity;
        def.friction = mFrictionRatio;
        def.restitution = mRestitutionRatio;

//        bodyDef.position.set(-hx, hy);
        bodyDef.position.set(-hx, 0);
        Body leftBody = mWorld.createBody(bodyDef);
        leftBody.createFixture(def);

        bodyDef.position.set(mappingView2Body(mWorldWidth) + hx, 0);
        Body rightBody = mWorld.createBody(bodyDef);
        rightBody.createFixture(def);
    }


    private Shape createCircleBody(View view) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(mappingView2Body(view.getWidth() / 2));
        return circleShape;
    }

    private Shape createPolygonBody(View view) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(mappingView2Body(view.getWidth() / 2), mappingView2Body(view.getHeight() / 2));
        return shape;
    }

    private float mappingView2Body(float view) {
        return view / mProportion;
    }

    private float mappingBody2View(float body) {
        return body * mProportion;
    }


    private void applyLinearImpulse(float x, float y, View view) {
        Body body = (Body) view.getTag(R.id.wd_view_body_tag);
        Vec2 vec2 = new Vec2(x, y);
        body.applyLinearImpulse(vec2, body.getPosition(), true);
    }

    private boolean isBodyView(View view) {
        Body body = (Body) view.getTag(R.id.wd_view_body_tag);
        return body != null;
    }

    private float getViewX(View view) {
        Body body = (Body) view.getTag(R.id.wd_view_body_tag);
        if (body != null) {
            return mappingBody2View(body.getPosition().x) - (view.getWidth() / 2);
        }
        return 0;
    }

    private float getViewY(View view) {
        Body body = (Body) view.getTag(R.id.wd_view_body_tag);
        if (body != null) {
            return mappingBody2View(body.getPosition().y) - (view.getHeight() / 2);
        }
        return 0;
    }

    public float getViewRotaion(View view) {
        Body body = (Body) view.getTag(R.id.wd_view_body_tag);
        if (body != null) {
            float angle = body.getAngle();
            return (angle / 3.14f * 180f) % 360;
        }
        return 0;
    }

    public void onSensorChanged(float x, float y) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (isBodyView(view)) {
                applyLinearImpulse(x, y, view);
            }
        }
    }

    public void onRandomChanged(){
        int childCount = viewGroup.getChildCount();
        float x = random.nextInt(800) - 800;
        float y = random.nextInt(800) - 800;
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (isBodyView(view)) {
                applyLinearImpulse(x, y, view);
            }
        }
    }

    /**
     * 根据半径获取多边形每个点的坐标位置
     *
     * @param n 多边形边数
     * @param r 半径
     * @return
     */
    public ArrayList polygon2(int n, int r) {
        float segmentlength = Double.valueOf(r * Math.sin(Math.PI / n)).floatValue();
        ArrayList<float[]> doubles = new ArrayList<>();
        double theta = 2 * Math.PI / n;
        for (int i = 0; i < n + 1; i++) {
            float x, y = 0f;
            x = Double.valueOf(r * Math.cos(theta * i)).floatValue();
            y = Double.valueOf(r * Math.sin(theta * i)).floatValue();
            float[] xy = new float[4];
            xy[0] = x + r;
            xy[1] = y + r;
            xy[2] = segmentlength;
            xy[3] = Double.valueOf(theta * i).floatValue();
            doubles.add(xy);
        }
        return doubles;
    }
}
